package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.ProductsService;

import java.util.List;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminProductsController {

    @Autowired
    private ProductsService productsService;

    /*@GetMapping("/adminProducts")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String adminProducts(Model model) {
        return "/admin/products";
    }*/

    @GetMapping("/adminProducts")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String products(Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        int pageSize = 8;
        List<Product> listProducts = productsService.getProducts();
        int totalPages = productsService.getTotalPages(listProducts);
        boolean moreProducts = true;
        //Page<Product> test = productsService.getProducts(page, pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        if (page<=totalPages-1){
            model.addAttribute("product", productsService.getProducts(page, pageSize));
        } else {
            moreProducts=false;
            model.addAttribute("product", null);
        }
        model.addAttribute("moreProducts", moreProducts);
        return "admin/products";
    }
    
    @PostMapping("/adminProducts/modifyProduct")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> handleFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("price") Float price) {
        productsService.modifyProduct(Long.parseLong(id), name, price, description);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminProducts")).build();
    }

    @GetMapping("adminProducts/removeProduct")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> removeAction(@RequestParam(name="id") long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminProducts")).build();
    }

    //TODO ADD NEW PRODUCT; MODIFY IMAGES; FIT DESCRIPTION IN THE CELL; PAGINATION
}
