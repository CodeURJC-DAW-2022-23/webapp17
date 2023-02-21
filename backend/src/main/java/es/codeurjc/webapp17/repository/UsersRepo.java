package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.UserProfile;

public interface UsersRepo extends JpaRepository<UserProfile, Long>{
    List<UserProfile> findByEmail(String email);
}
