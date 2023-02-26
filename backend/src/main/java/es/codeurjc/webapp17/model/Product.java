package es.codeurjc.webapp17.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
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

    private int numberOfImages; //If 0 == false

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
        this.images = new ArrayList<Image>();
        this.comments = new ArrayList<Comment>();
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


    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setNumberOfImages(int image) {
        this.numberOfImages = image;
    }

    public int getNumberOfImages() {
        return this.numberOfImages;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
}
