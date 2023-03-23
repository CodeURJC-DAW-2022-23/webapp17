package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.service.AdminService;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
    
    @Autowired
    private UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    private CartsService cartsService;


    @GetMapping("/cart")
    @NeedsSecurity(role=Tools.Role.USER)
    public String cart(Model model, HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);

        if(map.get("couponList")!=null){ 
            model.addAttribute("couponList", map.get("couponList"));
        }
        if(map.get("couponName")!=null){ 
            model.addAttribute("couponName", map.get("couponName"));
        }
        if(map.get("discount")!=null){ 
            model.addAttribute("discount", map.get("discount"));
        }
        if(map.get("couponApplied")!=null){ 
            model.addAttribute("couponApplied", map.get("couponApplied"));
        }
        if(map.get("totalPrice")!=null){ 
            model.addAttribute("totalPrice", map.get("totalPrice"));
        }
        if(map.get("cartItems")!=null){ 
            model.addAttribute("cartItems", map.get("cartItems"));
        }
        if(map.get("cartSize")!=null){ 
            model.addAttribute("cartSize", map.get("cartSize"));
        }
        if(map.get("existingCart")!=null){ 
            model.addAttribute("existingCart", map.get("existingCart"));
        }
        if(map.get("user")!=null){ 
            model.addAttribute("user", map.get("user"));
        }
        return "menu/cart";
    }

    @DeleteMapping("/deleteItem/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public void deleteItem(@PathVariable long id, HttpServletRequest request){
        cartsService.deleteItem(id, request);
    }


    @GetMapping("/decreaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> decreaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.decreaseQuantity(id, request);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/cart")).build();
    }


    @GetMapping("/increaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> increaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.increaseQuantity(id, request);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/cart")).build();
    }


    @GetMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public String checkout(Model model, HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);

        if(map.get("couponList")!=null){ 
            model.addAttribute("couponList", map.get("couponList"));
        }
        if(map.get("couponName")!=null){ 
            model.addAttribute("couponName", map.get("couponName"));
        }
        if(map.get("discount")!=null){ 
            model.addAttribute("discount", map.get("discount"));
        }
        if(map.get("couponApplied")!=null){ 
            model.addAttribute("couponApplied", map.get("couponApplied"));
        }
        if(map.get("totalPrice")!=null){ 
            model.addAttribute("totalPrice", map.get("totalPrice"));
        }
        if(map.get("cartItems")!=null){ 
            model.addAttribute("cartItems", map.get("cartItems"));
        }
        if(map.get("cartSize")!=null){ 
            model.addAttribute("cartSize", map.get("cartSize"));
        }
        if(map.get("existingCart")!=null){ 
            model.addAttribute("existingCart", map.get("existingCart"));
        }
        if(map.get("user")!=null){ 
            model.addAttribute("user", map.get("user"));
        }
        
        return "menu/checkout";
    }

    @PostMapping("/checkout")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> doCheckout(Model model, HttpServletRequest request) {
        ResponseEntity<Object> response = cartsService.doCheckout(request);
        return response;
    }

    @PostMapping("/redeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> redeem(@RequestParam(name="code") String code, HttpServletRequest request) {
        ResponseEntity<Object> response = cartsService.redeem(code, request);
        return response;
    }

    @PostMapping("/unredeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> unredeem(HttpServletRequest request) {
        ResponseEntity<Object> response = cartsService.unredeem(request);
        return response;
    }



}
