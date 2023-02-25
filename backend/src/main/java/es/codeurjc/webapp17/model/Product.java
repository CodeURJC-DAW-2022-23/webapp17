package es.codeurjc.webapp17.model;

import java.sql.Blob;
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

    private String summary;
    
    private String description;
    
    @Nonnull
    private Float price;

    private String[] tags;

    private Boolean image;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Image> images;

    //Getters, Constructors...
    
    public Product(String title, String description, float price, String[] tags) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.tags = tags;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public void setImageFile(List<Image> images) {
        this.images = images;
    }

    public Blob getImageFile() {
        return this.images.get(0).getImageFile();
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    public Boolean getImage() {
        return this.image;
    }

    
}
