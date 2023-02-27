package es.codeurjc.webapp17.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import es.codeurjc.webapp17.model.Image;
import es.codeurjc.webapp17.model.Product;

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

    @GetMapping("/products/{id}/image/{idImage}")
    public ResponseEntity<Object> downloadImage(@PathVariable long id,@PathVariable int idImage) throws SQLException {
	List<Product> product = products_service.getProductsRepo().findById(id);
	if (!product.isEmpty() && product.get(0).getImages().get(idImage).getImageFile() != null) {
		Resource file = new InputStreamResource(product.get(0).getImages().get(idImage).getImageFile().getBinaryStream());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg").contentLength(product.get(0).getImages().get(idImage).getImageFile().length()).body(file);
    } else {
	    return ResponseEntity.notFound().build();
	    }	
    }

    @GetMapping("/description")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String description(@RequestParam(name="id") long id, Model model) {
        model.addAttribute("product", products_service.getProductsRepo().findById(id));
        return "dishes/description";
    }
}
