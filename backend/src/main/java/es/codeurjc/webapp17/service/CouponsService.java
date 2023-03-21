package es.codeurjc.webapp17.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.codeurjc.webapp17.repository.CouponsRepo;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.model.UserProfile;

@Service
public class CouponsService {
    
    @Autowired
    private CouponsRepo coupons;

    @Autowired
    private UsersService usersService;

    public CouponsRepo getCouponsRepo(){
        return coupons;
    }

    public List<Coupon> getCoupons(){
        return getCouponsRepo().findAll();
    }

    public void removeCoupon(long id){
        Coupon coupon = getCouponsRepo().findById(id).get(0);
        getCouponsRepo().delete(coupon);
    }

    public void modifyCoupon(long id, int usesRemaining, float discount, String code, String newUserEmail){
        Coupon coupon = getCouponsRepo().findById(id).get(0);
        UserProfile user = usersService.getUsersRepo().findById(coupon.getUserId()).get();
        int n = 0;
        while(coupon.getId() != user.getCoupons().get(n).getId()){
            n++;
        }
        UserProfile newUser = usersService.getUsersRepo().findByEmail(newUserEmail).get(0);
        user.getCoupons().get(n).setUser(newUser);
        coupon.setUsesRemaining(usesRemaining);
        coupon.setCode(code);
        coupon.setDiscount(discount);
        if(!user.equals(newUser)){
            usersService.getUsersRepo().save(user);
        }
        usersService.getUsersRepo().save(newUser);
    }

    public void createCoupon(int usesRemaining, int discount, String code, String User){
        Coupon coupon = new Coupon(discount,code, usesRemaining);
        UserProfile user = usersService.getUsersRepo().findByEmail(User).get(0);
        coupon.setUser(user);
        user.getCoupons().add(coupon);
        usersService.getUsersRepo().saveAndFlush(user);
    }
}