package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.service.CartItemsService;
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
    private CartItemsService items_service;
    
    @GetMapping("/cart")
    @NeedsSecurity(role=Tools.Role.USER)
    public String cart(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
            boolean existing_cart = false;
            if ((user.getCart() != null)){
                if (user.getCart().getCartItems().size()!=0){
                    existing_cart = true;
                    List<CartItem> total_cart = user.getCart().getCartItems();
                    float total_price = 0;
                    int total_size=0;
                    for (CartItem cart_item : total_cart) {
                        for (int i = 0; i < cart_item.getQuantity(); i++) {
                            total_price = total_price + cart_item.getProduct().getPrice();
                            total_size++;
                        }
                    }
                    model.addAttribute("totalPrice", total_price);
                    model.addAttribute("cartItems", total_cart);
                    model.addAttribute("cartSize", total_size);
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
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        user.getCart().deleteCartItem(item);
        users_service.getUsers().saveAndFlush(user);
    }

    @GetMapping("/decreaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView decreaseQuantity(@PathVariable long id, HttpServletRequest request){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        if (user.getCart().getCartItems().get(n).getQuantity()>1) {
            user.getCart().getCartItems().get(n).decreaseQuantity();
        } else {
            deleteItem(id, request);
        }
        users_service.getUsers().saveAndFlush(user);
        return new ModelAndView("redirect:/cart");
    }

    @GetMapping("/increaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView increaseQuantity(@PathVariable long id, HttpServletRequest request){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        user.getCart().getCartItems().get(n).increaseQuantity();
        users_service.getUsers().saveAndFlush(user);
        return new ModelAndView("redirect:/cart");
    }

    @GetMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public String checkout(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
            if ((user.getCart() != null)){
                if (user.getCart().getCartItems().size()!=0){
                    List<CartItem> total_cart = user.getCart().getCartItems();
                    float total_price = 0;
                    int total_size=0;
                    for (CartItem cart_item : total_cart) {
                        for (int i = 0; i < cart_item.getQuantity(); i++) {
                            total_price = total_price + cart_item.getProduct().getPrice();
                            total_size++;
                        }
                    }
                    model.addAttribute("totalPrice", total_price);
                    model.addAttribute("cartItems", total_cart);
                    model.addAttribute("cartSize", total_size);
                }
            } else {}
            return "menu/checkout";
        }catch(NullPointerException ex){
            return "";
        }
    }

    @PostMapping("/redeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public HashMap<String,Object> redeem(@RequestParam(name="code") String code, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        List<Coupon> userCoupons = user.getCoupons();
        int discount = 0;
        int n = 0;
        try{
            while(!userCoupons.get(n).getCode().equals(code)){
                n++;
            }
            Coupon selectedCoupon = userCoupons.get(n);
            if(selectedCoupon.getUsesRemaining()>0){
                selectedCoupon.decreaseUse();
                users_service.getUsers().saveAndFlush(user);
                discount = selectedCoupon.getDiscount();  //Percentage of the discount
            }else{
                discount = -1; //No uses reamining
            }
        }catch (Exception e){
            //Discount does not exist
        }
        map.put(Integer.toString(discount),"true");
        return map;
    }

}
