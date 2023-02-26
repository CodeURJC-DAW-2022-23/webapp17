package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Nonnull
    @ManyToOne
    @JsonIgnore
    private UserProfile created_by;

    @Nonnull
    private int status;

    @Nonnull
    private Timestamp created_at;

    @Nonnull
    private Timestamp updated_at;

    @OneToMany(mappedBy="cart", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CartItem> cartItems;

    //Getters, Constructors...
}
