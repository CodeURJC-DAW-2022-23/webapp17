package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.AdminService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UsersService usersService;

    @GetMapping("/admin")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String adminMenu(Model model) {

        List<Long[]> productsQuery = adminService.getProductsRepo().getSales();

        List<Product> topProducts = productsQuery.stream().map(e -> adminService.getProductsRepo().findById(e[0]).get()).toList();
        List<Long> topSales = productsQuery.stream().map(e -> e[1]).toList();
        boolean salesExist = false;
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("topSales", topSales);
        if (!topProducts.isEmpty()){
            salesExist = true;
            model.addAttribute("salesExist", salesExist);
        }
        model.addAttribute("users", usersService.getUsersRepo().findAll().size());
        return "/admin/dashboard";
    }

}
