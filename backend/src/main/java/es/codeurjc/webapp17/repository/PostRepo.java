package es.codeurjc.webapp17.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp17.model.Post;
import jakarta.transaction.Transactional;

public interface PostRepo extends JpaRepository<Post, Long>{
    List<Post> findByTitle(String title);
}
