package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.model.ProductImage;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.ProductsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.net.URI;
import java.sql.Blob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/adminProducts")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public String products(Model model, @RequestParam(name="page", defaultValue = "0") int page, HttpServletRequest request) {
        List<Product> listProducts = productsService.getProducts();
        List<Product> shownProducts = new ArrayList<Product>();
        int pageSize = 8;
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)listProducts.size() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        Product product;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<listProducts.size()){
                product = listProducts.get(((page) * pageSize)+i);
                shownProducts.add(product);
            }
        }
        model.addAttribute("product", shownProducts);
        return "admin/products";
    }
    
    @PostMapping("/adminProducts/modifyProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("price") Float price) {
        productsService.modifyProduct(Long.parseLong(id), name, price, description);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminProducts")).build();
    }

    @GetMapping("adminProducts/removeProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeAction(@RequestParam(name="id") long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminProducts")).build();
    }

    @PostMapping("/adminProducts/addProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleCreationFormSubmissionAdmin(@RequestParam("name") String name,
                                       @RequestParam("price") String price,
                                       @RequestParam(value = "description", required = false) String description){
        
        String[] tags = {"Nuevo"};
        productsService.addProduct(name, Float.parseFloat(price), description, tags);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminProducts")).build();
    }

    
    @DeleteMapping("/adminProducts/removeImages/{id}")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> removeImage(@PathVariable long id, HttpServletRequest request) {
        List<Product> products = productsService.getProductsRepo().findById(id);
        products.get(0).getImages().clear();
        productsService.getProductsRepo().saveAndFlush(products.get(0));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/adminProducts/{id}/image")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile, HttpServletRequest request) throws IOException{
        List<Product> products = productsService.getProductsRepo().findById(id);
        Blob newImage = BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize());
        ProductImage ima = new ProductImage(newImage, products.get(0));
        products.get(0).getImages().add(ima);
        productsService.getProductsRepo().saveAndFlush(products.get(0));
        return ResponseEntity.ok().build();
    }

    //TODO MANAGE TAGS; MODIFY IMAGES
}
