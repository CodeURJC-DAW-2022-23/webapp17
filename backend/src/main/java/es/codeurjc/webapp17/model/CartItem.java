package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
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
    private Timestamp created_at;

    //Getters, Constructors...
}
