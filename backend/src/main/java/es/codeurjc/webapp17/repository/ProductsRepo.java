package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.Product;

public interface ProductsRepo extends JpaRepository<Product, Long>{
    //List<Product> findByTitle(String title);
    List<Product> findById(long id);
    Page<Product> findById(long id, Pageable page);

    @Query(value="SELECT PRODUCT_ID, SUM(QUANTITY) FROM CART JOIN CART_ITEM ON CART_ITEM.CART_ID=CART.ID WHERE NOT STATUS="
    +Cart.STATUS_DONE+" GROUP BY PRODUCT_ID ORDER BY SUM(QUANTITY) DESC", nativeQuery = true)
    List<Long[]> getSales();
}
