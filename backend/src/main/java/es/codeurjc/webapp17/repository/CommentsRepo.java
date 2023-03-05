package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;

public interface CommentsRepo extends JpaRepository<Comment, Long>{
    List<Comment> findByProduct(Product product);
    Page<Comment> findByProduct(Product product, Pageable page);

    @Query(value="SELECT AVG(RATING) AS rating_avg FROM COMMENT;", nativeQuery = true)
    float getAvgRating();

    @Query(value="SELECT COUNT(*) AS total_comments FROM COMMENT;", nativeQuery = true)
    int getTotalComments();
}
