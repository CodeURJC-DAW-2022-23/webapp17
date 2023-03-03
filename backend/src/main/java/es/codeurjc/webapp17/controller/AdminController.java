package es.codeurjc.webapp17.controller;

import org.springframework.web.bind.annotation.GetMapping;
import es.codeurjc.webapp17.tools.NeedsSecurity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class AdminController {

    @GetMapping("/adminMenu")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String adminMenu(Model model) {
        return "admin/menu";
    }
    
}
