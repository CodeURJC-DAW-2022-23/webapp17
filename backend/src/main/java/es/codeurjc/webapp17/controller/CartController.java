package es.codeurjc.webapp17.controller;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    //In swagger product title, price, etc do not appear
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

    //Can only be Get?
    @GetMapping("/decreaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView decreaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.decreaseQuantity(id, request);
        return new ModelAndView("redirect:/cart");
    }
    //Can only be Get?
    @GetMapping("/increaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public ModelAndView increaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.increaseQuantity(id, request);
        return new ModelAndView("redirect:/cart");
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
        try{
            UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            List<CartItem> listProductsSold = user.getCart().getCartItems();
            cartsService.confirmOrder(usersService.getUsersRepo()
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
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
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
                usersService.getUsersRepo().saveAndFlush(user);
            }
        }catch (Exception e){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unredeem")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> unredeem(HttpServletRequest request) {
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
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
                usersService.getUsersRepo().saveAndFlush(user);
            }
        }catch (Exception e){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }



}
