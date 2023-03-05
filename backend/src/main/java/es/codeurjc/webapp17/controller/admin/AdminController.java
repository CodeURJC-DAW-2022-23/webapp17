package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.AdminService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;

import java.util.List;

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

    @Autowired
    ProductsService productsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    CartsService cartsService;

    @GetMapping("/admin")
    @NeedsSecurity(role=Tools.Role.ADMIN)
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
        model.addAttribute("users", usersService.getUsersRepo().getTotalUsers());
        model.addAttribute("products", productsService.getProductsRepo().getTotalProducts());
        model.addAttribute("ratingAVG", commentsService.getCommentsRepo().getAvgRating());
        model.addAttribute("totalComments", commentsService.getCommentsRepo().getTotalComments());
        model.addAttribute("totalOrders", cartsService.getCartsRepo().getTotalOrders());

        return "/admin/dashboard";
    }

}
