package es.codeurjc.webapp17.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.codeurjc.webapp17.tools.Tools.HashMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long provider_id;
    
    @ManyToOne
    @JsonIgnore
    private UserProfile user_profile;

    private HashMethod hasher;

    private String password_hash;
    private String password_salt;
}
