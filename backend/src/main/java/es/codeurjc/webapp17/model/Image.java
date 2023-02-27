package es.codeurjc.webapp17.model;

import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idImage;

    private boolean firstOne;

    private int positionInProduct;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    //Getters, Constructors...

    public Image(){

    }

    public int getPositionInProduct() {
        return positionInProduct;
    }

    public void setPositionInProduct(int positionInProduct) {
        this.positionInProduct = positionInProduct;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public boolean isFirstOne() {
        return firstOne;
    }

    public void setFirstOne(boolean firstOne) {
        this.firstOne = firstOne;
    }

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    public Image(Blob imageFile) {
        this.imageFile = imageFile;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
}