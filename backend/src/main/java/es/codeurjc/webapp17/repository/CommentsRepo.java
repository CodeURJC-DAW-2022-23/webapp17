package es.codeurjc.webapp17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp17.model.UserProfile;

public interface CommentsRepo extends JpaRepository<UserProfile, Long>{
}
