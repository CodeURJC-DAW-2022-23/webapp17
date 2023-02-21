package es.codeurjc.webapp17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp17.model.UserProfile;

public interface CartsRepo extends JpaRepository<UserProfile, Long>{
}
