package es.codeurjc.webapp17.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.CartItem;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    UsersService usersService;

    @GetMapping("/products")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String products(Model model, @RequestParam(defaultValue = "0") int page) {
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
        return "dishes/order";
    }

    @GetMapping("/products/{id}/image/{idImage}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id,@PathVariable int idImage) throws SQLException {
        List<Product> product = productsService.getProductsRepo().findById(id);
        if (!product.isEmpty() && product.get(0).getImages().get(idImage).getImageFile() != null) {
            return product.get(0).getImages().get(idImage).toHtmEntity();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
    }

    @PostMapping("/addToCart")
    @NeedsSecurity(role=Tools.Role.USER)
    public @ResponseBody Map<String,Object> addToCart(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Product product = productsService.getProductsRepo().findById(id).get(0);
        try{
            UserProfile user = usersService.getUsers().findByEmail(request.getUserPrincipal().getName()).get(0);
            user.getCart().addCartItem(new CartItem(product,user.getCart()));
            usersService.getUsers().saveAndFlush(user);
            map.put("ok","true");
        }catch(NullPointerException ex){
            map.put("Login", "true");
        }
        return map;
    }
}
