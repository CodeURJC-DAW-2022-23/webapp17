package es.codeurjc.webapp17.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void modifyProduct(long id, String title, Float price, String description, String[] tags){
        Product product = products.findById(id).get(0);
        product.setTtile(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setTags(tags);
        getProductsRepo().saveAndFlush(product);
    }

    public void deleteProduct(long id){
        products.delete(products.getReferenceById(id));
    }

    public void editProductImage(long id, int index){ 
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

    public Map<String, Object> products(int page) {
        Map<String,Object> map = new HashMap<String, Object>();
        int pageSize = 8;
        List<Product> listProducts = getProducts();
        int totalPages = getTotalPages(listProducts);
        boolean moreProducts = true;
        //Page<Product> test = productsService.getProducts(page, pageSize);
        map.put("totalPages", totalPages);
        map.put("currentPage", page);
        if (page<=totalPages-1){
            map.put("product", getProducts(page, pageSize));
        } else {
            moreProducts=false;
            map.put("product", null);
        }
        map.put("moreProducts", moreProducts);
        return map;
    }
}