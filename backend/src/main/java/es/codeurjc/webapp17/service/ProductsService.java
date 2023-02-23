package es.codeurjc.webapp17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.ProductsRepo;
import jakarta.annotation.PostConstruct;

@Service
public class ProductsService {
    
    @Autowired
    private ProductsRepo products;

    @PostConstruct
    public void init(){
        products.save(new Product(1,"Paella", "https://carlosarroces.com/wp-content/uploads/2021/01/alcachofas-cenital-800x604.jpg", "Paella de marisco", "Paella de marisco", 25));
    }

    public ProductsRepo getProducts() {
        return products;
    }

}