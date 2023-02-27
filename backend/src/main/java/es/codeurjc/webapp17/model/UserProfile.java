package es.codeurjc.webapp17.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "UserProfile")
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
    private String email_validated = "";

    private String forgot_password = "";

    @Nonnull
    private Tools.Role role = Role.USER;

    private String phone;

    @OneToMany(mappedBy="user_profile", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Credential> credentials = new ArrayList<Credential>();

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
        createCredential(Credential.INTERNAL_STRING, password);
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
        return email_validated;
    }
    public void setEmailValidated(String email_validated) {
        this.email_validated = email_validated;
    }

    public String getForgotPassword() {
        return forgot_password;
    }

    public void setForgotPassword(String forgot_password) {
        this.forgot_password = forgot_password;
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

    public void createCredential(String provider, String password_hash){
        Credential credential = new Credential(provider, password_hash, this);        
        this.credentials.add(credential);
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
        createCredential(provider, password_hash);
    }
}

