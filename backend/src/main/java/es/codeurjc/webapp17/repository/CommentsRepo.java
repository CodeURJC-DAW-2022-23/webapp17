package es.codeurjc.webapp17.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Comment;

public interface CommentsRepo extends JpaRepository<Comment, Long>{
}
