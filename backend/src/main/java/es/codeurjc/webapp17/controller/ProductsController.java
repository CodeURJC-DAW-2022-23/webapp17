package es.codeurjc.webapp17.controller;

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

    //TODO Descargar varias imagenes de un objeto
    @GetMapping("/products/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
	List<Product> product = products_service.getProductsRepo().findById(id);
	if (!product.isEmpty() && product.get(0).getImages().get(0).getImageFile() != null) {
		Resource file = new InputStreamResource(product.get(0).getImages().get(0).getImageFile().getBinaryStream());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").contentLength(product.get(0).getImages().get(0).getImageFile().length()).body(file);
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
