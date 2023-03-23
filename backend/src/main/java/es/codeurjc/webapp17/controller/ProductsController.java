package es.codeurjc.webapp17.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping("/products")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String products(Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        Map<String,Object> map = productsService.productsPaginated(page, request);

        model.addAttribute("totalPages", map.get("totalPages"));
        model.addAttribute("currentPage", map.get("currentPage"));
        if (map.get("product")!=null){
            model.addAttribute("product", map.get("product"));
        }
        model.addAttribute("moreProducts", map.get("moreProducts"));

        //Recommended Products
        if(map.get("recomended_product")!=null){
            model.addAttribute("recomended_product", map.get("recomended_product"));
            model.addAttribute("has_recomended", map.get("has_recomended"));
        }
    
        return "dishes/order";
    }

    @GetMapping("/products/{id}/image/{idImage}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id,@PathVariable int idImage) throws SQLException {
        ResponseEntity<Object> response = productsService.downloadImage(id, idImage);
        return response;
    }


    @PostMapping("/addToCart")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> addToCart(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.addToCart(id, request);
        return map;
    }
}
