package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Cart;

public interface CartsRepo extends JpaRepository<Cart, Long>{
    List<Cart> findById(long id);
}
