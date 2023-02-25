package es.codeurjc.webapp17.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import es.codeurjc.webapp17.tools.Tools;
import es.codeurjc.webapp17.tools.Tools.Role;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO role
    
    @Nonnull
    private String name;
    
    private String bio;

    @Nonnull
    @Column(unique=true)
    private String email;

    @Nonnull
    private Boolean email_validated;

    @Nonnull
    private Tools.Role role;

    private String phone;

    @OneToMany(mappedBy="user_profile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Credential> credentials;

    @OneToMany(mappedBy="created_by", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Cart> carts;

    @OneToMany(mappedBy="user_profile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy="user_profile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Booking> bookings;

    public UserProfile(){}
    
    //Getters, Constructors...
    public UserProfile(String email, String name, String password){
        super();
        this.email = email;
        this.name = name;
        Credential credential = new Credential(Credential.INTERNAL_STRING, password, this);        
        this.credentials = new ArrayList<Credential>();
        this.credentials.add(credential);
        this.role = Role.USER;
    }

    public User toUser(){
        User user = new User(email, getInternalCredential().getPasswordHash(), AuthorityUtils.createAuthorityList(role.getCode()));
        return user;
    }

    public String getName() {
        return name;
    }
    
    public Tools.Role getRole() {
        return role;
    }

    public void setRole(Tools.Role role) {
        this.role = role;
    }

    public Credential getInternalCredential(){
        for(Credential cred : this.credentials){
            if(cred.getProviderId().equals(Credential.INTERNAL_STRING)){
                return cred;
            }
        }
        assert true : "Internal provider does not exist on user.";
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

    public void updateCredential(String provider, String password_hash){
        Credential cred = null;
        // This should be fine as credentials should have less than 3 elements.
        // TODO If credentials becomes bigger implement a query.
        for(int i = 0; i < credentials.size(); i++){
            if(credentials.get(i).getProviderId().equals(provider)){
                cred = credentials.get(i);
                cred.setPasswordHash(password_hash);
                credentials.set(i, cred);
                return;
            }
        }
        assert cred != null : "Given provider does not exist";
    }
}

