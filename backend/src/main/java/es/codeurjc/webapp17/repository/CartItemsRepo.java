package es.codeurjc.webapp17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp17.model.UserProfile;

public interface CartItemsRepo extends JpaRepository<UserProfile, Long>{
}
