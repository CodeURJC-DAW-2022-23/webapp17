package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @ManyToOne
    @JsonIgnore
    private UserProfile userProfile;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @Nonnull
    private int rating;

    @Nonnull
    private String description;

    @Nonnull
    private Timestamp createdAt;

    //Getters, Constructors...
    
    public Comment(){}

    public Comment(int rating, String description, Timestamp createdAt) {
        this.rating = rating;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Comment(int rating, String description, Timestamp createdAt, UserProfile user, Product product) {
        this.rating = rating;
        this.description = description;
        this.createdAt = createdAt;
        this.userProfile = user;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setUser(UserProfile user) {
        this.userProfile = user;
    }
    
}
