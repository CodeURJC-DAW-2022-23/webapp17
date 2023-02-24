package es.codeurjc.webapp17.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp17.model.Product;

public interface ProductsRepo extends JpaRepository<Product, Long>{
    List<Product> findByTitle(String title);
}
