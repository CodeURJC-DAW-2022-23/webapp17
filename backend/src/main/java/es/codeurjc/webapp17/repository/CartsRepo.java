package es.codeurjc.webapp17.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.UserProfile;

public interface CartsRepo extends JpaRepository<Cart, Long>{
    List<Cart> findById(long id);
    Page<Cart> findByStatusNot(int status, Pageable pageable);
    Page<Cart> findByCreatedByAndStatus(UserProfile profile, int status, Pageable pageable);
    Page<Cart> findByCreatedByAndStatusNot(UserProfile profile, int status, Pageable pageable);
}
