package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.service.UsersServive;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class HelloController {

    @Autowired
    UsersServive users_service;

    @GetMapping("/")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String index(Model model) {
        model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "index";
    }

    @GetMapping("/info")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String info(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "info/info";
    }

    @GetMapping("/user")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String user(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "info/user";
    }

    @GetMapping("/login")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String login(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "login/login";
    }

    @GetMapping("/register")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String register(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "login/register";
    }

    @GetMapping("/menu")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String menu(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "menu/menu";
    }

    @GetMapping("/order")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String order(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "dishes/order";
    }

    @GetMapping("/book")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String book(Model model) {
        //model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "dishes/book";
    }

}

