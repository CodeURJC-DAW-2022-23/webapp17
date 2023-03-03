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
@Table(name = "CouponImage")
public class CouponImage extends Image{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idImage;

    @Nonnull
    @OneToOne
    @JsonIgnore
    private Coupon couponLinked;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    //Getters, Constructors...
    public CouponImage(){}

    public CouponImage(Blob imageBlob, Coupon couponLinked){
        setCouponLinked(couponLinked);
        setImageFile(imageBlob);
    }

    public void setCouponLinked(Coupon couponLinked) {
        this.couponLinked = couponLinked;
    }

    public Coupon getCouponLinked() {
        return couponLinked;
    }
}