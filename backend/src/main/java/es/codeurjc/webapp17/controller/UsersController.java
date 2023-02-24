package es.codeurjc.webapp17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class UsersController {
    
    @GetMapping("/user")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String user(Model model) {
        return "info/user";
    }
    
}
