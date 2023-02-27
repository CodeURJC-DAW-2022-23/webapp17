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

    public static final String INTERNAL_STRING = "internal";
    public static final String GOOGLE_STRING = "google";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long credential_id;
    
    @Nonnull
    @ManyToOne
    @JsonIgnore
    private UserProfile user_profile;

    private String provider_id;

    private String password_hash;

    public Credential(){}

    public Credential(String provider_id, String password_hash, UserProfile profile){
        this.provider_id = provider_id;
        this.password_hash = password_hash;
        this.user_profile = profile;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getProviderId() {
        return provider_id;
    }
}
