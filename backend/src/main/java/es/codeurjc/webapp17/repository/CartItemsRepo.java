package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.CartItem;

public interface CartItemsRepo extends JpaRepository<CartItem, Long>{
    List<CartItem> findById(long id);
    List<CartItem> findAll(); 
}
