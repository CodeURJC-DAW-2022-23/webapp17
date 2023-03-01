package es.codeurjc.webapp17.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Credential")
public class Credential {

    public static final String INTERNALSTRING = "internal";
    public static final String GOOGLESTRING = "google";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long credentialId;
    
    @Nonnull
    @ManyToOne
    @JsonIgnore
    private UserProfile userProfile;

    private String providerId;

    private String passwordHash;

    public Credential(){}

    public Credential(String providerId, String passwordHash, UserProfile profile){
        this.providerId = providerId;
        this.passwordHash = passwordHash;
        this.userProfile = profile;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getProviderId() {
        return providerId;
    }
}
