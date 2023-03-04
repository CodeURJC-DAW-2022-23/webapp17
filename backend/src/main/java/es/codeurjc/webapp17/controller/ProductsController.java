package es.codeurjc.webapp17.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import es.codeurjc.webapp17.model.Comment;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @Autowired
    UsersService usersService;

    @GetMapping("/products")
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

        if(request.getUserPrincipal() != null){
            UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
            if(user != null && !user.getOrders().isEmpty()){
                List<Long> recomendedProducts = usersService.getUsersRepo()
                    .getRecomendedByProductList(user.getOrders().get(user.getOrders().size()-1).getId());
                List<Product> products = productsService.getProductsRepo().findAllById(recomendedProducts);
                if(products.size() > 0){
                    model.addAttribute("recomended_product", products.subList(0, Math.min(products.size(), 4)));
                    model.addAttribute("has_recomended", true);
                }
            }
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

    @PostMapping("/products/{id}/addComment")
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> addComment(HttpServletRequest request, @PathVariable long id,@RequestParam(name = "content") String content, 
    @RequestParam(name = "stars") int stars) throws SQLException {
        List<Product> product = productsService.getProductsRepo().findById(id);
        if (!product.isEmpty() && request.getUserPrincipal() != null) {
            UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
            if (user != null) {
                Comment comment = new Comment(stars,content,
                    new Timestamp(System.currentTimeMillis()), user, product.get(0));
                product.get(0).getComments().add(comment);
                productsService.getProductsRepo().saveAndFlush(product.get(0));
                return ResponseEntity.ok().build();
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
    }

    @PostMapping("/addToCart")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> addToCart(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Product product = productsService.getProductsRepo().findById(id).get(0);
        try{
            UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            user.getCart().addCartItem(new CartItem(product,user.getCart()));
            usersService.getUsersRepo().saveAndFlush(user);
            map.put("ok","true");
        }catch(NullPointerException ex){
            map.put("Login", "true");
        }
        return map;
    }
}
