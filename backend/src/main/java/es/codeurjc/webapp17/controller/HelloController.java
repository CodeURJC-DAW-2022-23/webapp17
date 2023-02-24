package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class HelloController {

    @Autowired
    UsersService users_service;
    ProductsService products_service;

    @GetMapping("/")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String index(Model model) {
        model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
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

}

