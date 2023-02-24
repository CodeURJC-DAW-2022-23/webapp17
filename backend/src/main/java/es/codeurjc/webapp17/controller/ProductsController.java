package es.codeurjc.webapp17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class ProductsController {

    @GetMapping("/products")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String products(Model model) {
        return "dishes/order";
    }
}
