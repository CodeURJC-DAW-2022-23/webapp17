package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @ManyToOne
    @JsonIgnore
    private UserProfile user_profile;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @Nonnull
    private int rating;

    @Nonnull
    private String description;

    @Nonnull
    private Timestamp created_at;

    //Getters, Constructors...
}
