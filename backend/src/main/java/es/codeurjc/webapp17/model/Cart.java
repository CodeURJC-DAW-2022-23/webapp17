package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {

    public static final int STATUS_ORDERED = 1;
    public static final int STATUS_DONE = 2;
    public static final int STATUS_NEW = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Nonnull
    @ManyToOne
    @JsonIgnore
    private UserProfile createdBy;

    @Nonnull
    @Column(name="status")
    private int status = STATUS_NEW;

    @Nonnull
    private Timestamp createdAt;

    @Nonnull
    private Timestamp updatedAt;

    @ManyToOne
    private Coupon coupon;

    @OneToMany(mappedBy="cart", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CartItem> cartItems;

    //Getters, Constructors...

    public Cart(UserProfile user){
        this.createdBy = user;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.cartItems = new ArrayList<CartItem>();
        this.status = STATUS_NEW;
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

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartItem> getCartItems(){
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public int totalSize(){
        List<CartItem> total_cart = getCartItems();
        int total_size = 0;
        for (CartItem cart_item : total_cart) {
            total_size += cart_item.getQuantity();
        }
        return total_size;
    }

    public float getDiscount() {
        if(coupon != null)
            return coupon.getDiscount();
        return 0;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public boolean hasDiscount(){
        return coupon != null;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public float totalPrice(){
        List<CartItem> total_cart = getCartItems();
        float total_price = 0;
        for (CartItem cart_item : total_cart) {
            total_price += cart_item.getProduct().getPrice()*cart_item.getQuantity();
        }
        total_price -= total_price*getDiscount()*0.01;
        return total_price;
    }

    public boolean isPreparing(){
        return status == STATUS_ORDERED;
    }
}
