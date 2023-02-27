package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class HelloController {

    @Autowired
    UsersService users_service;

    @GetMapping("/")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String index(Model model) {
        model.addAttribute("message", users_service.getUsers().findByEmail("test@example.com"));
        model.addAttribute("user_name", "Pedro");
        return "index";
    }

    @GetMapping("/info")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String info(Model model) {
        return "info/info";
    }

    @GetMapping("/menu")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String menu(Model model) {
        return "menu/menu";
    }

    @GetMapping("/cart")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String cartMenu(Model model) {
        return "menu/cart";
    }

}

