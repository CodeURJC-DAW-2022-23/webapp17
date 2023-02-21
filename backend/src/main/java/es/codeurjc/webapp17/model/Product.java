package es.codeurjc.webapp17.model;

import java.util.List;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nonnull
    private String title;
    
    private String picture;

    private String summary;
    
    private String description;
    
    @Nonnull
    private Float price;

    private String[] tags;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CartItem> cartItems;

    //Getters, Constructors...

    public Product(){}

    public Product(String title, String picture, String summary, String description, Float price, String[] tags) {
        this.title = title;
        this.picture = picture;
        this.summary = summary;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }

    
}
