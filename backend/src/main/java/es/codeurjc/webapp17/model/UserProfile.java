package es.codeurjc.webapp17.model;

import java.time.Instant;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Where;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import es.codeurjc.webapp17.tools.Tools;
import es.codeurjc.webapp17.tools.Tools.Role;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserProfile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Nonnull
    private String name;
    
    private String bio = "";

    @Nonnull
    @Column(unique=true)
    private String email;

    private String emailValidated = "";

    private String forgotPassword = "";

    @Nonnull
    private List<Tools.Role> role = new ArrayList<>();

    private String phone;

    private Timestamp lastModified;

    
    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonIgnore
    private List<Credential> credentials = new ArrayList<Credential>();
    
    @OneToMany(mappedBy="createdBy", cascade=CascadeType.ALL)
    @Where(clause = "status=0")
    private List<Cart> cart;

    @OneToMany(mappedBy="createdBy", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Cart> orders;

    @OneToOne(mappedBy="imageOwner", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private ProfileImage image;

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Booking> bookings = new ArrayList<Booking>();

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Coupon> coupons;

    public UserProfile(){}
    
    //Getters, Constructors...
    public UserProfile(String email, String name, String password){
        super();
        this.email = email;
        this.name = name;
        this.bio = "";   
        createCredential(Credential.INTERNALSTRING, password);
        this.cart = new ArrayList<Cart>();
        this.cart.add(new Cart(this));
        this.role.add(Role.USER);
        this.coupons = new ArrayList<Coupon>();
        this.lastModified = Timestamp.from(Instant.now());
    }

    @JsonAnyGetter
    public Map<String,Object> otherFields() {
        Map<String,Object> extra = new HashMap<String,Object>();
        extra.put("cartLength", this.getCart().getCartItems().size());
        return extra;
    }

    public User toUser(){
        User user;
        if(getInternalCredential() != null)
            user = new User(email, getInternalCredential().getPasswordHash(), AuthorityUtils.createAuthorityList(getRoleString()));
        else
            user = new User(email, credentials.get(0).getPasswordHash(), AuthorityUtils.createAuthorityList(getRoleString()));
        return user;
    }

    public Long getID(){
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tools.Role> getRole() {
        return role;
    }

    public void addRole(Tools.Role role){
        if(this.role != null) this.role.add(role); 
    }

    public boolean hasRole(Role role){
        if(this.role != null) return this.role.contains(role);
        return false;
    }

    public String[] getRoleString(){
        String r = "";
        for(Tools.Role rol : role){
            r+=rol.getCode()+" ";
        }
        return r.split(" ");
    }

    public String getEmailValidated() {
        return emailValidated;
    }
    public void setEmailValidated(String emailValidated) {
        this.lastModified = Timestamp.from(Instant.now());
        this.emailValidated = emailValidated;
    }

    public String getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(String forgotPassword) {
        this.lastModified = Timestamp.from(Instant.now());
        this.forgotPassword = forgotPassword;
    }

    public void refreshLastModified(){
        this.lastModified = Timestamp.from(Instant.now());
    }

    public Credential getInternalCredential(){
        for(Credential cred : this.credentials){
            if(cred.getProviderId().equals(Credential.INTERNALSTRING)){
                return cred;
            }
        }
        return null;
    }

    public Credential getCredential(String provider){
        for(Credential cred : this.credentials){
            if(cred.getProviderId().equals(provider)){
                return cred;
            }
        }
        return null;
    }

    public void createCredential(String provider, String passwordHash){
        Credential credential = new Credential(provider, passwordHash, this);        
        this.credentials.add(credential);
    }

    public void updateCredential(String provider, String passwordHash){
        Credential cred = null;
        // This should be fine as credentials should have less than 3 elements.
        // TODO If credentials becomes bigger implement a query.
        for(int i = 0; i < credentials.size(); i++){
            if(credentials.get(i).getProviderId().equals(provider)){
                cred = credentials.get(i);
                cred.setPasswordHash(passwordHash);
                credentials.set(i, cred);
                return;
            }
        }
        createCredential(provider, passwordHash);
    }

    public ProfileImage getImage() {
        return image;
    }

    public void setImage(ProfileImage image) {
        this.image = image;
    }

    public void setCart(Cart cart){
        if(this.cart != null){
            this.cart.set(0, cart);
            return;
        }
        this.cart = new ArrayList<Cart>();
        this.cart.add(cart);
    }

    public Cart getCart(){
        return cart.get(0);
    }

    public void emptyCart(){
        cart.clear();
        cart.add(new Cart(this));
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public List<Coupon> getCoupons(){
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons){
        this.coupons = coupons;
    }
    public List<Cart> getOrders() {
        return orders;
    }

    public void setOrders(List<Cart> orders) {
        this.orders = orders;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}

