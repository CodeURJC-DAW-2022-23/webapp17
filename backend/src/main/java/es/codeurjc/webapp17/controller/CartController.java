package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Product;
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
            if (user.getCart() != null){
                existing_cart = true;
                model.addAttribute("cartItems", user.getCart().getCartItems());
                model.addAttribute("existingCart", existing_cart);
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

    @GetMapping("/setQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public void setQuantity(@PathVariable long id, HttpServletRequest request, int quantity){
        CartItem item = items_service.getCartItemsRepo().findById(id).get(0);
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        user.getCart().getCartItems().get(n).setQuantity(quantity);
        users_service.getUsers().saveAndFlush(user);
    }

    @GetMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public String checkout(Model model, HttpServletRequest request) {
        try {
            UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
            boolean existing_cart = false;
            if (user.getCart() != null){
                existing_cart = true;
                model.addAttribute("cartItems", user.getCart().getCartItems());
                model.addAttribute("existingCart", existing_cart);
            } else {}
            return "menu/checkout";
        }catch(NullPointerException ex){
            
            return "";
        }
    }

}
