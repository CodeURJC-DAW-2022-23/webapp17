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

    public void modifyProduct(long id, String title, Float price, String description){
        Product product = products.findById(id).get(0);
        product.setTtile(title);
        product.setPrice(price);
        product.setDescription(description);
        getProductsRepo().saveAndFlush(product);
    }

    public void deleteProduct(long id){
        products.delete(products.getReferenceById(id));
    }

    public void editProductImage(long id, int index){  //TODO Menu for modifying different images of the product
        Product product = products.findById(id).get(0);
        product.setImages(null);
    }

    public Page<Product> getProducts(int numPage, int pageSize) {
        Pageable pageable = PageRequest.of(numPage, pageSize);
        return products.findAll(pageable);
    }

    public int getTotalPages(List<Product> listProducts) {
        int pageSize = 8;
        int totalProducts = listProducts.size();
        int totalPages = totalProducts / pageSize;
        if (totalProducts % pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }

    public void addProduct(String name, Float price, String description, String[] tags){
        Product product = new Product(name, description, price, tags);
        getProductsRepo().saveAndFlush(product);
    }

}