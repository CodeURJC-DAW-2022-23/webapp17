package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;

public interface CommentsRepo extends JpaRepository<Comment, Long>{
    List<Comment> findByProduct(Product product);
    Page<Comment> findByProduct(Product product, Pageable page);
}
