package es.codeurjc.webapp17.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;

@Controller
public class CommentsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    UsersService usersService;

    /*@GetMapping("/description")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String description(@RequestParam(name="id") long id, Model model) {
        model.addAttribute("product", productsService.getProductsRepo().findById(id));
        return "dishes/description";
    }*/

    @GetMapping("/description")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String description(@RequestParam(name="id") long id, Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        int pageSize = 8;
        Product product = productsService.getProducts().get((int) id - 1);
        List<Comment> listComments = product.getComments();
        int totalPages = commentsService.getTotalPagesComments(listComments);
        boolean moreProducts = true;
        //Page<Product> test = productsService.getProducts(page, pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("product", product);
        //model.addAttribute("product", productsService.getProductsRepo().findById(id));
        if (page<=totalPages-1){
            model.addAttribute("productComments", commentsService.getComments(product, page, pageSize));
        } else {
            moreProducts=false;
            model.addAttribute("productComments", null);
        }
        model.addAttribute("moreComments", moreProducts);
        if(request.getUserPrincipal() != null)
            model.addAttribute("userProfile_id", usersService.getUser(request.getUserPrincipal().getName()).getID());
        return "dishes/description";
    }
}
