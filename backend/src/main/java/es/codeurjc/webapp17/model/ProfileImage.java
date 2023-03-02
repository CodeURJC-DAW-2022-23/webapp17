package es.codeurjc.webapp17.model;

import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProfileImage")
public class ProfileImage extends Image{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idImage;

    @Nonnull
    @OneToOne
    @JsonIgnore
    private UserProfile imageOwner;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    //Getters, Constructors...
    public ProfileImage(){}

    public ProfileImage(Blob imageBlob, UserProfile user){
        setUser(user);
        setImageFile(imageBlob);
    }

    public void setUser(UserProfile user) {
        this.imageOwner = user;
    }

    public UserProfile getUser() {
        return imageOwner;
    }
}