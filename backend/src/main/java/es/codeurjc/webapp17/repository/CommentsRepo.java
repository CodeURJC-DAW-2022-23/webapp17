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

    @Query(value="select avg(rating) as rating_avg from comment", nativeQuery = true)
    float getAvgRating();

    @Query(value="select count(*) as total_comments from comment", nativeQuery = true)
    int getTotalComments();
}
