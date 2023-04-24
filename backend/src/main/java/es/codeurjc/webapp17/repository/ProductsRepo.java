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

    @Query(value="select product_id, sum(quantity) from cart join cart_item on cart_item.cart_id=cart.id where not status="
    +Cart.STATUS_NEW+" group by product_id order by sum(quantity) desc", nativeQuery = true)
    List<Long[]> getSales();

    @Query(value="select count(*) as total_products from product", nativeQuery = true)
    int getTotalProducts();
}
