package es.codeurjc.webapp17.model;

import java.util.List;

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

    @Nonnull
    private String name;
    
    private String bio;

    @Nonnull
    @Column(unique=true)
    private String email;

    @OneToMany(mappedBy="user_profile", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Credential> credentials;

    public UserProfile(){}

    public UserProfile(String name, String email){
        super();
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
}
