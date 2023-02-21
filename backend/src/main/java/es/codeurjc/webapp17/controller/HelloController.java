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
    public String handle(Model model) {
        model.addAttribute("message", users_service.getUsers().findByEmail("helloworld@email.com").get(0).getName());
        return "index";
    }

}

