package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.service.CouponsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.model.UserProfile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminCouponsController {

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/adminCoupons")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public String coupons(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Coupon> listCoupons = couponsService.getCoupons();
        List<Coupon> shownCoupons = new ArrayList<Coupon>();
        int pageSize = 8;
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)listCoupons.size() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        Coupon coupon;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<listCoupons.size()){
                coupon = listCoupons.get(((page) * pageSize)+i);
                shownCoupons.add(coupon);
            }
        }
        List<UserProfile> listUsers = usersService.getUsers();
        model.addAttribute("user", listUsers);
        model.addAttribute("coupon", shownCoupons);
        return "admin/coupons";
    }

    @PostMapping("/adminCoupons/modifyCoupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleEditFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("userId") String userId,
                                       @RequestParam("userEmail") String newUserEmail,
                                       @RequestParam("uses") int uses) {
        couponsService.modifyCoupon(Long.parseLong(id), uses, Float.parseFloat(discount), code, newUserEmail);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminCoupons")).build();
    }

    @GetMapping("/adminCoupons/removeCoupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeAction(@RequestParam(name="id") String id){
        couponsService.removeCoupon(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminCoupons")).build();
    }

    @PostMapping("/adminCoupons/createCoupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleCreationFormSubmission(@RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("usesRemaining") int uses,
                                       @RequestParam("user") String user) {
        couponsService.createCoupon(uses, Integer.parseInt(discount), code, user);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminCoupons")).build();
    }
        
}
