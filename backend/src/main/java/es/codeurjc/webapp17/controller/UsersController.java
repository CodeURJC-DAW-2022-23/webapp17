package es.codeurjc.webapp17.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsersController {
    
    @Autowired
    private UsersService users;

    @GetMapping("/profile")
    @NeedsSecurity(role=Tools.Role.USER)
    public String user(Model model) {
        return "info/user";
    }

    @GetMapping("/getUserInfo")
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
    
}
