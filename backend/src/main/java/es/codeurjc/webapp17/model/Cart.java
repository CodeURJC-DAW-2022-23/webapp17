package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Nonnull
    @OneToOne
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

    public Cart(UserProfile user){
        this.created_by = user;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.cartItems = new ArrayList<CartItem>();
    }

    public Cart(){
    }

    public int positionOfCartItem(CartItem item){
        List<CartItem> items = getCartItems();
        int n = 0;
        try{
            while (!items.get(n).getProduct().equals(item.getProduct())){
                n++;
            }
            return n;
        }catch(IndexOutOfBoundsException e){
            return -1; //Cart item is not in this cart
        }

    }

    public void addCartItem(CartItem item){
        List<CartItem> items = getCartItems();
        int n = positionOfCartItem(item);
        if (n == -1){
            items.add(item);
        }else{
            items.get(n).setQuantity(items.get(n).getQuantity()+1);
        }
    }

    public void deleteCartItem(CartItem item){
        List<CartItem> items = getCartItems();
        int n = positionOfCartItem(item);
        if(n>-1){
            items.remove(n);
        }
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public List<CartItem> getCartItems(){
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
