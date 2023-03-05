package es.codeurjc.webapp17.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.ProductsRepo;

@Service
public class AdminService {

    @Autowired
    ProductsRepo productsRepo;

    public ProductsRepo getProductsRepo() {
        return productsRepo;
    }

}
