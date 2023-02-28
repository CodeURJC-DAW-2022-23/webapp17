package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.service.CartItemsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
    
    @Autowired
    private UsersService users_service;

    @Autowired
    private CartItemsService items_service;
    
    @GetMapping("/cartItems")
    @NeedsSecurity(role=Tools.Role.USER)
    public String products(Model model, HttpServletRequest request) {
        UserProfile user = users_service.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        model.addAttribute("cartItems", user.getCart().getCartItems());
        return "menu/cart";
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

}
