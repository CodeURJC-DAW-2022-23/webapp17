package es.codeurjc.webapp17.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.ProductsRepo;

@Service
public class ProductsService {
    
    @Autowired
    private ProductsRepo products;

    public ProductsRepo getProductsRepo(){
        return products;
    }

    public List<Product> getProducts() {
        return products.findAll();
    }

    /*public List<Product> getProducts(int page, Pageable page) {
        return products.findAll();
    }*/

    public Page<Product> getProducts(int num_page, int page_size) {
        Pageable pageable = PageRequest.of(num_page, page_size);
        return products.findAll(pageable);
    }

    public int getTotalPages(List<Product> products) {
        int page_size = 8;
        int total_products = products.size();
        int total_pages = total_products / page_size;
        if (total_products % page_size != 0) {
            total_pages++;
        }
        return total_pages;
    }

}