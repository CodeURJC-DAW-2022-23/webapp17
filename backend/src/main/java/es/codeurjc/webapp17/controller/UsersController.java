package es.codeurjc.webapp17.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.view.RedirectView;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsersController {
    
    @Autowired
    private UsersService users;

    @GetMapping("/profile")
    @NeedsSecurity(role=Tools.Role.USER)
    public String user(Model model, HttpServletRequest request) {
        UserProfile user = users.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
        model.addAttribute("has_image", user.getImage() != null);
        model.addAttribute("user_profile_image", "/user/"+user.getID()+"/image");
        model.addAttribute("user_name", user.getName() != null ? user.getName() : user.getEmail().split("@")[0]);
        model.addAttribute("user_email", user.getEmail());
        model.addAttribute("user_bio", user.getBio() != null ? user.getBio() : Tools.tr("BIO_NOT_FOUND", "ES"));
        return "info/user";
    }

    @GetMapping("/user/getUserInfo")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> getUserInfo(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){ 
            Map<String,Object> ui = users.getUserInfo(request.getUserPrincipal().getName());
            if(ui != null)
                return ui;
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
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<UserProfile> user = users.getUsers().findById(id);
        if (user.isPresent() && user.get().getImage() != null) {
            return user.get().getImage().toHtmEntity();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
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
