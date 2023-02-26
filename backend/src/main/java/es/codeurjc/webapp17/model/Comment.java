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
    
    public Comment(){}

    public Comment(int rating, String description, Timestamp created_at) {
        this.rating = rating;
        this.description = description;
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public UserProfile getUser_profile() {
        return user_profile;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setUser(UserProfile user) {
        this.user_profile = user;
    }
    
}
