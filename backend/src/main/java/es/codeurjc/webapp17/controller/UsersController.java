package es.codeurjc.webapp17.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.Image;
import es.codeurjc.webapp17.model.ProfileImage;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsersController {
    
    @Autowired
    private UsersService users;

    @Autowired
    private CartsService carts;

    @GetMapping("/profile")
    @NeedsSecurity(role=Tools.Role.USER)
    public String user(Model model, HttpServletRequest request,  @RequestParam(defaultValue = "0", name="orderPage") int page) {
        UserProfile user = users.getUser(request.getUserPrincipal().getName());
        model.addAttribute("has_image", user.getImage() != null);
        model.addAttribute("user_id", user.getID());
        model.addAttribute("user_name", user.getName() != null ? user.getName() : user.getEmail().split("@")[0]);
        model.addAttribute("user_email", user.getEmail());
        model.addAttribute("user_bio", user.getBio() != null ? user.getBio() : Tools.tr("BIO_NOT_FOUND", "ES"));
        model.addAttribute("user_booking", user.getBookings());


        Page<Cart> p = carts.getUserOrders(user, page, 4);
        if(p.getNumberOfElements() != 0){
            Tools.PaginationMustache pag = new Tools.PaginationMustache(p.hasNext(),
            p, page);
            model.addAttribute("pag_order", pag);
        }
        
        return "info/user";
    }

    @GetMapping("/user/getUserInfo")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> getUserInfo(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){ 
            Map<String,Object> ui = users.getUserInfo(request.getUserPrincipal().getName());
            if(ui != null){
                return ui;
            }    
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("error", "true");
        return map;
        
    }

    @PostMapping("/user/changeName")
    @NeedsSecurity(role = Tools.Role.USER)
    public @ResponseBody Map<String,Object> changeNamePost(HttpServletRequest request, 
    @RequestParam(name="newName", required = true) String name){
        HashMap<String, Object> map = new HashMap<>();
        if(request.getUserPrincipal() != null){
            if(users.changeName(request.getUserPrincipal().getName(), name) != null){
                map.put("changed", "true");
                return map;
            }
        }
        map.clear();
        map.put("error", "true");

        return map;
    }

    @PostMapping("/user/changeDescription")
    @NeedsSecurity(role = Tools.Role.USER)
    public @ResponseBody Map<String,Object> changeDescriptionPost(HttpServletRequest request, 
    @RequestParam(name="newDescription", required = true) String name){
        HashMap<String, Object> map = new HashMap<>();
        if(request.getUserPrincipal() != null){
            if(users.changeDescription(request.getUserPrincipal().getName(), name) != null){
                map.put("changed", "true");
                return map;
            }
        }
        map.clear();
        map.put("error", "true");

        return map;
    }

    @GetMapping("/user/{id}/image")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, IOException {
        Optional<UserProfile> user = users.getUsersRepo().findById(id);
        if (user.isPresent()) {
            if(user.get().getImage() != null)
                return user.get().getImage().toHtmEntity();
                
            InputStream in = (new ClassPathResource("static/images/Nestea.jpg")).getInputStream();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg").body(new InputStreamResource(in));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
    }

    @PostMapping("/user/{id}/image")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile, HttpServletRequest request) throws SQLException, IOException {
        Optional<UserProfile> user = users.getUsersRepo().findById(id);
        if (user.isPresent()) {
            if(request.getUserPrincipal().getName().equals(user.get().getEmail()) 
            || users.getUser(request.getUserPrincipal().getName()).hasRole(Tools.Role.ADMIN)){
                users.changeImage(user.get().getEmail(), BlobProxy.generateProxy(
                imageFile.getInputStream(), imageFile.getSize()));
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}/order")
    @NeedsSecurity(role=Tools.Role.USER)
    public String getOrder(@PathVariable long id, Model model, 
    HttpServletRequest request, @RequestParam(name="orderId", required = true) long orderId) throws SQLException {
        Optional<UserProfile> user = users.getUsersRepo().findById(id);
        if(request.getUserPrincipal() != null){
            if (user.isPresent() && request.getUserPrincipal().getName().equals(user.get().getEmail())) {
                List<Cart> orderList = carts.getCartsRepo().findById(orderId);
                if(!orderList.isEmpty() && orderList.get(0).getStatus() != Cart.STATUS_NEW){
                    model.addAttribute("base_domain", "../../");
                    model.addAttribute("totalPrice", orderList.get(0).totalPrice());
                    model.addAttribute("order", orderList.get(0));
                    return "info/order";
                }
            }
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user/delete")
    @NeedsSecurity(role = Tools.Role.USER)
    public Map<String,Object> changeDescriptionPost(HttpServletRequest request) throws ServletException{
        HashMap<String, Object> map = new HashMap<>();
        if(request.getUserPrincipal() != null){
            if(!users.getUserInfo(request.getUserPrincipal().getName()).containsKey("error")){
                users.removeUser(request.getUserPrincipal().getName());
                request.logout();
            }
        }
        map.put("error", "true");
        return map;
    }
    
}
