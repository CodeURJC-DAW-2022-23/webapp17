package es.codeurjc.webapp17.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.CartItemsRepo;
import es.codeurjc.webapp17.repository.CartsRepo;
import es.codeurjc.webapp17.repository.UsersRepo;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartsService {
    
    @Autowired
    private UsersService usersService;

    @Autowired
    private CartItemsRepo cartItems;

    @Autowired
    private PermissionsService permissionsService;

    @Autowired
    private CartsRepo carts;

    @Autowired
    private UsersRepo users;

    @Autowired
    private JavaMailSender emailSender;


    public CartItemsRepo getCartItemsRepo(){
        return cartItems;
    }


    public CartsRepo getCartsRepo() {
        return carts;
    }

    public List<CartItem> getCartItems() {
        return cartItems.findAll();
    }

    public Page<Cart> getUserOrders(UserProfile user, int numPage, int pageSize){
        Pageable pageable = PageRequest.of(numPage, pageSize);
        return getCartsRepo().findByCreatedByAndStatusNot(user, Cart.STATUS_NEW, pageable);
    }


    public Page<Cart> getUserOrders(UserProfile user){
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return getCartsRepo().findByCreatedByAndStatus(user, Cart.STATUS_ORDERED, pageable);
    }


    public void confirmOrder(Cart order){
        order.setStatus(Cart.STATUS_ORDERED);
        carts.saveAndFlush(order);

        UserProfile profile = order.getCreatedBy();
        profile.emptyCart();
        users.saveAndFlush(profile);

        sendOrderMail(order);
    }

    public void confirmOrder(long id){
        confirmOrder(carts.findById(id).get());
    }


    public void sendOrderMail(Cart order){
        try {
            PDDocument doc = Tools.getConfirmationPdf(order);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.save(out);
            doc.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ByteArrayDataSource source =  new ByteArrayDataSource(out.toByteArray(), "application/octet-stream");
            Tools.sendEmailWithAttachment(order.getCreatedBy().getEmail(), 
            "Tu pedido ha sido confirmado", 
            "Aquí tienes el documento de confirmación: ", "invoice.pdf", source, emailSender);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public HashMap<String,Object> cartAndCheckout(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        if (!permissionsService.isUserLoggedIn(request, usersService)) {
            map.put("notLogged", true);
            return map;
        }
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        boolean existing_cart = false;
        if ((user.getCart() != null)){
            if (user.getCart().getCartItems().size()!=0){
                List<Coupon> userCoupons = user.getCoupons();
                if(!userCoupons.isEmpty())
                    map.put("couponList", userCoupons);
                existing_cart = true;
                List<CartItem> totalCart = user.getCart().getCartItems();
                float totalPrice = user.getCart().totalPrice();
                int totalSize=user.getCart().totalSize();
                if (user.getCart().hasDiscount()){
                    float discount = user.getCart().getDiscount();
                    map.put("couponName", user.getCart().getCoupon().getCode());
                    if (discount!=-1){
                        map.put("discount", discount);
                    }
                    map.put("couponApplied", user.getCart().hasDiscount());
                } else {
                    map.put("couponName", "No se ha aplicado ningún cupón");
                }
                map.put("totalPrice", totalPrice);     
                map.put("cartItems", totalCart);
                map.put("cartSize", totalSize);
                map.put("existingCart", existing_cart);
                map.put("user", user);
            }
        } else {}
        return map;
    }

    public HashMap<String,Object> cartAndCheckoutApi(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        if (!permissionsService.isUserLoggedIn(request, usersService)) {
            map.put("notLogged", true);
            return map;
        }
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        boolean existing_cart = false;
        if ((user.getCart() != null)){
            if (user.getCart().getCartItems().size()!=0){
                List<Coupon> userCoupons = user.getCoupons();
                if(!userCoupons.isEmpty()){
                    map.put("couponList", userCoupons);
                } else {
                    //map.put("couponList", 0);
                }
                    
                existing_cart = true;
                List<CartItem> totalCart = user.getCart().getCartItems();
                float totalPrice = user.getCart().totalPrice();
                int totalSize=user.getCart().totalSize();
                if (user.getCart().hasDiscount()){
                    float discount = user.getCart().getDiscount();
                    map.put("coupon", user.getCart().getCoupon());
                    //map.put("couponName", user.getCart().getCoupon().getCode());
                    if (discount!=-1){
                        //map.put("discount", discount);
                    }
                    map.put("couponApplied", user.getCart().hasDiscount());
                } else {
                    //map.put("couponName", "No se ha aplicado ningún cupón");
                }
                map.put("totalPrice", totalPrice);     
                map.put("cartItems", totalCart);
                map.put("cartSize", totalSize);
                map.put("user", user);
            }
        }
        map.put("existingCart", existing_cart);
        return map;
    }


    public void deleteItem(long id, HttpServletRequest request){
        CartItem item = getCartItemsRepo().findById(id).get(0);
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        user.getCart().deleteCartItem(item);
        usersService.getUsersRepo().saveAndFlush(user);
    }


    public void decreaseQuantity(long id, HttpServletRequest request){
        
        CartItem item = getCartItemsRepo().findById(id).get(0);
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        //long id2 = id;
        if (user.getCart().getCartItems().get(n).getQuantity()>1) {
            user.getCart().getCartItems().get(n).decreaseQuantity();
            usersService.getUsersRepo().saveAndFlush(user);
        } else {
            deleteItem(id, request);
        }
    
    }


    public void increaseQuantity(long id, HttpServletRequest request){
        //long idPrueba = id;
        CartItem item = getCartItemsRepo().findById(id).get(0);
        UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
        int n = user.getCart().positionOfCartItem(item);
        user.getCart().getCartItems().get(n).increaseQuantity();
        usersService.getUsersRepo().saveAndFlush(user);
    }


    public ResponseEntity<Object> doCheckout(HttpServletRequest request){
        try{
            //UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            //List<CartItem> listProductsSold = user.getCart().getCartItems();
            confirmOrder(usersService.getUsersRepo()
            .findByEmail(request.getUserPrincipal().getName()).get(0).getCart());
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/checkout")).build();
    }

    public ResponseEntity<Object> redeem(String code, HttpServletRequest request) {
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/coupon")).build();
    }


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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/couponFree")).build();
    }

    // TODO Fix this
    public Boolean changeOrderState(long id){
        try{
        Page<Cart> carti = getCartsRepo().findByStatusNot(Cart.STATUS_NEW, PageRequest.of(0, 256));
        Optional<Cart> cart = Optional.empty();
        for(Cart carte : carti.getContent()){
            if(carte.getId() == id) cart = Optional.of(carte);
        }
        if(cart != null){
            cart.get().setStatus(Cart.STATUS_DONE);
            getCartsRepo().saveAndFlush(cart.get());
            return true;
        }else{
            return false;
        }
        }catch(Exception ex){
            ex.printStackTrace();
            return true;
        }
    }

    public Boolean deleteOrder(long id){
        Optional<Cart> cart = getCartsRepo().findById(id);
        if(cart != null){
            getCartsRepo().delete(cart.get());
            return true;
        }else{
            return false;
        }
    }

}