package es.codeurjc.webapp17.service;

import java.util.List;
import java.util.Optional;

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

    public Boolean removeCoupon(long id){
        List<Coupon> coupon = getCouponsRepo().findById(id);
        if(!coupon.isEmpty()){
            getCouponsRepo().delete(coupon.get(0));
            return true;
        }else{
            return false;
        }
    }

    /*
     * Returns 0 if coupon modified succesfully
     * Returns 1 if coupon does not exist
     * Returns 2 if new user does not exist
     */
    public int modifyCoupon(long id, int usesRemaining, float discount, String code, String newUserEmail){  
        List<Coupon> coupon = getCouponsRepo().findById(id);
        if(coupon != null){
            Optional<UserProfile> user = usersService.getUsersRepo().findById(coupon.get(0).getUserId());
            int n = 0;
            while(coupon.get(0).getId() != user.get().getCoupons().get(n).getId()){
                n++;
            }
            List<UserProfile> newUser = usersService.getUsersRepo().findByEmail(newUserEmail);
            if(!newUser.isEmpty()){
                user.get().getCoupons().get(n).setUser(newUser.get(0));
                coupon.get(0).setUsesRemaining(usesRemaining);
                coupon.get(0).setCode(code);
                coupon.get(0).setDiscount(discount);
                if(!user.equals(newUser)){
                    usersService.getUsersRepo().save(user.get());
                }
                usersService.getUsersRepo().save(newUser.get(0));
                return 0;
            }else{
                return 2;
            }
        }else{
            return 1;
        }
    }

    public Boolean createCoupon(int usesRemaining, int discount, String code, String User){
        Coupon coupon = new Coupon(discount,code, usesRemaining);
        List<UserProfile> user = usersService.getUsersRepo().findByEmail(User);
        if(!user.isEmpty()){
            coupon.setUser(user.get(0));
            user.get(0).getCoupons().add(coupon);
            usersService.getUsersRepo().saveAndFlush(user.get(0));
            return true;
        }else{
            return false;
        }
    }
}