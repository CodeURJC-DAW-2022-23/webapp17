package es.codeurjc.webapp17.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private UserProfile userProfile;

    @Nonnull
    private float discount; //It will be a percentage

    @Nonnull
    private String code;

    @Nonnull
    private int usesRemaining;

    @OneToOne(mappedBy="couponLinked", cascade=CascadeType.ALL, orphanRemoval=true)
    private CouponImage image;

    //Getters, constructors...

    public Coupon(){}

    public Coupon(int discount, String code, int usesRemaining) {
        this.discount = discount;
        this.code = code;
        this.usesRemaining = usesRemaining;
    }

    public float getDiscount() {
        return discount;
    }

    public String getCode() {
        return code;
    }

    public int getUsesRemaining() {
        return usesRemaining;
    }

    public void decreaseUse(){
        usesRemaining--;
    }

    public void setUser(UserProfile userProfile){
        this.userProfile = userProfile;
    }

    public CouponImage getImage() {
        return image;
    }

    public void setImage(CouponImage image) {
        this.image = image;
    }
}
