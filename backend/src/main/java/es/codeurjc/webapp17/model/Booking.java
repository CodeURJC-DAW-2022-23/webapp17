package es.codeurjc.webapp17.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Nonnull
    @ManyToOne
    @JsonIgnore
    private UserProfile userProfile;

    @Nonnull
    private Timestamp when;

    @Nonnull
    private Boolean confirmation;

    //Getters, Constructors...
}
