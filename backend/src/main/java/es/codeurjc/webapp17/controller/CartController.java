package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
    
    @Autowired
    private UsersService users_service;

    @Autowired
    ProductsService products_service;

    @Autowired
    private CartsService items_service;
    
    boolean couponApplied = false;
    String couponName;
    float discount = 0;


    @GetMapping("/cart")
    @NeedsSecurity(role=Tools.Role.USER)
    public String cart(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            boolean existing_cart = false;
            if ((user.getCart() != null)){
                if (user.getCart().getCartItems().size()!=0){
                    existing_cart = true;
                    List<CartItem> totalCart = user.getCart().getCartItems();
                    float totalPrice = user.getCart().totalPrice();
                    int totalSize=user.getCart().totalSize();
                    if (couponApplied){
                        model.addAttribute("couponName", couponName);
                        if (discount!=-1){
                            totalPrice = totalPrice - totalPrice*(discount/100);
                            model.addAttribute("discount", discount);
                        } else {
                            couponApplied = false;
                        }
                    } else {
                        model.addAttribute("couponName", "No se ha aplicado ningún cupón");
                    }
                    model.addAttribute("totalPrice", totalPrice);
                    model.addAttribute("cartItems", totalCart);
                    model.addAttribute("cartSize", totalSize);
                    model.addAttribute("existingCart", existing_cart);
                    model.addAttribute("couponApplied", couponApplied);
                }
            } else {}
            return "menu/cart";
        }catch(NullPointerException ex){
            return "";
        }
    }

    @GetMapping("/deleteItem/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public void deleteItem(@PathVariable long id, HttpServletRequest request){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        user.getCart().deleteCartItem(item);
        users_service.getUsersRepo().saveAndFlush(user);
    }

    @GetMapping("/decreaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView decreaseQuantity(@PathVariable long id, HttpServletRequest request){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        if (user.getCart().getCartItems().get(n).getQuantity()>1) {
            user.getCart().getCartItems().get(n).decreaseQuantity();
            users_service.getUsersRepo().saveAndFlush(user);
        } else {
            deleteItem(id, request);
        }
        return new ModelAndView("redirect:/cart");
    }

    @GetMapping("/increaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView increaseQuantity(@PathVariable long id, HttpServletRequest request){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        user.getCart().getCartItems().get(n).increaseQuantity();
        users_service.getUsersRepo().saveAndFlush(user);
        return new ModelAndView("redirect:/cart");
    }

    @GetMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public String checkout(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            if ((user.getCart() != null)){
                if (user.getCart().getCartItems().size()!=0){
                    List<CartItem> totalCart = user.getCart().getCartItems();
                    float totalPrice = 0;
                    int totalSize=0;
                    for (CartItem cart_item : totalCart) {
                        for (int i = 0; i < cart_item.getQuantity(); i++) {
                            totalPrice = totalPrice + cart_item.getProduct().getPrice();
                            totalSize++;
                        }
                    }
                    if (couponApplied){
                        model.addAttribute("couponName", couponName);
                        if (discount!=-1){
                            model.addAttribute("discount", discount);
                            totalPrice = totalPrice - totalPrice*(discount/100);
                        } else {
                            couponApplied = false;
                        }
                    } else {
                        model.addAttribute("couponName", "No se ha aplicado ningún cupón");
                    }
                    model.addAttribute("totalPrice", totalPrice);
                    model.addAttribute("cartItems", totalCart);
                    model.addAttribute("cartSize", totalSize);
                    model.addAttribute("couponApplied", couponApplied);
                    model.addAttribute("user", user);
                }
            } else {}
            return "menu/checkout";
        }catch(NullPointerException ex){
            return "";
        }
    }

    @PostMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> doCheckout(Model model, HttpServletRequest request) {
        try{
            items_service.confirmOrder(users_service.getUsersRepo()
            .findByEmail(request.getUserPrincipal().getName()).get(0).getCart());
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView redeem(@RequestParam(name="code") String code, HttpServletRequest request, int cartOrCheckout) {
        HashMap<String,Object> map = new HashMap<>();
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        List<Coupon> userCoupons = user.getCoupons();
        int n = 0;
        try{
            while(!userCoupons.get(n).getCode().equals(code)){
                n++;
            }
            Coupon selectedCoupon = userCoupons.get(n);
            couponApplied = true;
            if(selectedCoupon.getUsesRemaining()>0){
                selectedCoupon.decreaseUse();
                users_service.getUsersRepo().saveAndFlush(user);
                couponName = selectedCoupon.getCode();
                discount = selectedCoupon.getDiscount();  //Percentage of the discount
            }else{
                discount = -1; //No uses reamining
            }
        }catch (Exception e){
            //Discount does not exist
        }
        map.put(Float.toString(discount),"true");
        if (cartOrCheckout==1){
            return new ModelAndView("redirect:/checkout");
        } else {
            return new ModelAndView("redirect:/cart");
        }
        //return map;
    }

}
