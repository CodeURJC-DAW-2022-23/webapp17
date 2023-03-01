package es.codeurjc.webapp17.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

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
    
    private String bio;

    @Nonnull
    @Column(unique=true)
    private String email;

    @Nonnull
    private String emailValidated = "";

    private String forgotPassword = "";

    @Nonnull
    private Tools.Role role = Role.USER;

    private String phone;

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Credential> credentials = new ArrayList<Credential>();

    @OneToOne(mappedBy="createdBy", cascade=CascadeType.ALL, orphanRemoval=true)
    private Cart cart;

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy="userProfile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Booking> bookings;

    public UserProfile(){}
    
    //Getters, Constructors...
    public UserProfile(String email, String name, String password){
        super();
        this.email = email;
        this.name = name;   
        createCredential(Credential.INTERNALSTRING, password);
        this.cart = new Cart(this);
        this.role = Role.USER;
    }

    public User toUser(){
        User user;
        if(getInternalCredential() != null)
            user = new User(email, getInternalCredential().getPasswordHash(), AuthorityUtils.createAuthorityList(role.getCode()));
        else
            user = new User(email, credentials.get(0).getPasswordHash(), AuthorityUtils.createAuthorityList(role.getCode()));
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

    public Tools.Role getRole() {
        return role;
    }

    public String getEmailValidated() {
        return emailValidated;
    }
    public void setEmailValidated(String emailValidated) {
        this.emailValidated = emailValidated;
    }

    public String getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(String forgotPassword) {
        this.forgotPassword = forgotPassword;
    }

    public void setRole(Tools.Role role) {
        this.role = role;
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

    public void setCart(Cart cart){
        this.cart = cart;
    }

    public Cart getCart(){
        return cart;
    }
}

