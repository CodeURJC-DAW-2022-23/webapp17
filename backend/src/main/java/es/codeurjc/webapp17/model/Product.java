package es.codeurjc.webapp17.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    private List<ProductImage> images;

    //Getters, Constructors...
    
    public Product(String title, String description, float price, String[] tags) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.images = new ArrayList<ProductImage>();
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

    public void setDescription(String description){
        this.description = description;
    }

    public void setTtile(String title){
        this.title = title;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }


    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductImage> getImages() {
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
