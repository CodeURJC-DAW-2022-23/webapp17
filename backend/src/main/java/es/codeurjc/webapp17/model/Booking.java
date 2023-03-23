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
    private String bookTime;

    @Nonnull
    private Boolean confirmation;

    @Nonnull
    private int people;

    private String number;

    //Getters, Constructors...
    public Booking(){}

    public Booking(UserProfile user, String bookString, int people, String number){
        this.userProfile = user;
        this.bookTime = bookString;
        this.people = people;
        this.number = number;
        confirmation = false;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPeople() {
        return people;
    }

    public String getNumber() {
        return number;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

}
