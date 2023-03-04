package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
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


    @GetMapping("/cart")
    @NeedsSecurity(role=Tools.Role.USER)
    public String cart(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            boolean existing_cart = false;
            if ((user.getCart() != null)){
                if (user.getCart().getCartItems().size()!=0){
                    List<Coupon> userCoupons = user.getCoupons();
                    if(!userCoupons.isEmpty())
                        model.addAttribute("couponList", userCoupons);
                    existing_cart = true;
                    List<CartItem> totalCart = user.getCart().getCartItems();
                    float totalPrice = user.getCart().totalPrice();
                    int totalSize=user.getCart().totalSize();
                    // TODO: Un-spageti this
                    if (user.getCart().hasDiscount()){
                        float discount = user.getCart().getDiscount();
                        model.addAttribute("couponName", user.getCart().getCoupon().getCode());
                        if (discount!=-1){
                            model.addAttribute("discount", discount);
                        }
                        model.addAttribute("couponApplied", user.getCart().hasDiscount());
                    } else {
                        model.addAttribute("couponName", "No se ha aplicado ningún cupón");
                    }
                    model.addAttribute("totalPrice", totalPrice);
                    model.addAttribute("cartItems", totalCart);
                    model.addAttribute("cartSize", totalSize);
                    model.addAttribute("existingCart", existing_cart);
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
                    if (user.getCart().hasDiscount()){
                        float discount = user.getCart().getDiscount();
                        model.addAttribute("couponName", "couponName");
                        if (discount!=-1){
                            model.addAttribute("discount", discount);
                        }
                    } else {
                        model.addAttribute("couponName", "No se ha aplicado ningún cupón");
                    }
                    model.addAttribute("totalPrice", totalPrice);
                    model.addAttribute("cartItems", totalCart);
                    model.addAttribute("cartSize", totalSize);
                    model.addAttribute("couponApplied", user.getCart().hasDiscount());
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
    public ResponseEntity<Object> redeem(@RequestParam(name="code") String code, HttpServletRequest request) {
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        List<Coupon> userCoupons = user.getCoupons();
        int n = 0;
        try{
            while(!userCoupons.get(n).getCode().equals(code)){
                n++;
            }
            Coupon selectedCoupon = userCoupons.get(n);
            if(selectedCoupon.getUsesRemaining()>0 && !user.getCart().hasDiscount()){
                user.getCart().setCoupon(selectedCoupon);
                selectedCoupon.decreaseUse();
                users_service.getUsersRepo().saveAndFlush(user);
            }
        }catch (Exception e){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unredeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> unredeem(HttpServletRequest request) {
        UserProfile user = users_service.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        List<Coupon> userCoupons = user.getCoupons();
        int n = 0;
        try{
            
            if(user.getCart().hasDiscount()){
                while(!userCoupons.get(n).getCode().equals(user.getCart().getCoupon().getCode())){
                    n++;
                }
                Coupon selectedCoupon = userCoupons.get(n);

                user.getCart().setCoupon(null);
                selectedCoupon.increaseUse();
                users_service.getUsersRepo().saveAndFlush(user);
            }
        }catch (Exception e){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }



}
