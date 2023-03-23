package es.codeurjc.webapp17.controller;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;

//Controller for getting information about individual products and adding comments to them.
@Controller
public class CommentsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    UsersService usersService;


    @PostMapping("/products/{id}/addComment")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> addComment(HttpServletRequest request, @PathVariable long id,@RequestParam(name = "content") String content, 
    @RequestParam(name = "stars") int stars) throws SQLException {
        ResponseEntity<Object> response = productsService.addComment(request, id, content, stars);
        return response;
    }


    @GetMapping("/description")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String description(@RequestParam(name="id") long id, Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.descriptionProduct(id, page, request);
        model.addAttribute("totalPages", map.get("totalPages"));
        model.addAttribute("currentPage", map.get("currentPage"));
        model.addAttribute("product", map.get("product"));
        if(map.get("productComments")!=null){
            model.addAttribute("productComments", map.get("productComments"));
        }
        model.addAttribute("moreComments", map.get("moreComments"));
        if(map.get("userProfile_id")!=null){
            model.addAttribute("userProfile_id", map.get("userProfile_id"));
        }
        return "dishes/description";
    }

    
}
