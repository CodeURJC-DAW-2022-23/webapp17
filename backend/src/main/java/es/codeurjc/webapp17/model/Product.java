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
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Product(){}

    public Product(long id, String title, String picture, String description, float price, String[] tags) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }

    public Product(long id, String title, String picture, String description, float price, String[] tags, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.comments = comments;
    }
    
}
