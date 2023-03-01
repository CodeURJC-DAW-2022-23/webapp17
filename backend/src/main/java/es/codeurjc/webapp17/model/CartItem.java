package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nonnull
    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @Nonnull
    @ManyToOne
    @JsonIgnore
    private Product product;

    @Nonnull
    private Float price;

    @Nonnull
    private int quantity;

    @Nonnull
    private Timestamp createdAt;

    //Getters, Constructors...

    public CartItem(){
        
    }

    public CartItem(Product product,Cart cart){
        this. product = product;
        this.price = product.getPrice();
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.quantity = 1;
        this.cart = cart;
    }

    public Product getProduct(){
        return product;
    }
    
    public void increaseQuantity(){
        this.quantity ++;
    }

    public void decreaseQuantity(){
        this.quantity --;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
