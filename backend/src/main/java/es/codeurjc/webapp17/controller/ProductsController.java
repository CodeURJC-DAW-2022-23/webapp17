package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class ProductsController {

    @Autowired
    ProductsService products_service;

    @GetMapping("/products")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String products(Model model) {
        model.addAttribute("product", products_service.getProducts());
        return "dishes/order";
    }

    @GetMapping("/description")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String description(@RequestParam(name="id") long id, Model model) {
        model.addAttribute("product", products_service.getProductsRepo().findById(id));
        return "dishes/description";
    }
}
