package es.codeurjc.webapp17.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.CouponsRepo;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class CouponsController {
    
    @Autowired
    CouponsRepo couponsRepo;


    @GetMapping("/coupon/{id}/image")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, IOException {
        List<Coupon> coupon = couponsRepo.findById(id);
        if (!coupon.isEmpty()) {
            if(coupon.get(0).getImage() != null)
                return coupon.get(0).getImage().toHtmEntity();
            return ResponseEntity.notFound().build();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
    }
    

}
