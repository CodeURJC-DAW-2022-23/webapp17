package es.codeurjc.webapp17.controller.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.service.CouponsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(Tools.API_HEADER+"/coupons/")
public class CouponsApiController {

    @Autowired
    private CouponsService couponsService;

    @Operation(summary = "Get coupons of every user sorted by pages of 8")
	@ApiResponses(value = { 
			@ApiResponse(
				responseCode = "200", 
				description = "Coupons loaded succesfully", 
				content = {@Content(
						mediaType = "application/json"
				)}
			),
			@ApiResponse(
                responseCode = "403", 
                description = "User not authorized, login with an admin account", 
                content = @Content
            ),
            @ApiResponse(
				responseCode = "404", 
				description = "Coupons not found", 
				content = @Content
				),
	})
	@GetMapping("/coupons")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public Object showCoupons(Model model, @RequestParam(defaultValue = "0") int page) {
		HashMap<String, Object> map = new HashMap<>();
        List<Coupon> listCoupons = couponsService.getCoupons();
        List<Coupon> shownCoupons = new ArrayList<Coupon>();
        int pageSize = 8;
        Coupon coupon;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<listCoupons.size()){
                coupon = listCoupons.get(((page) * pageSize)+i);
                shownCoupons.add(coupon);
            }
        }
        map.put("coupon", shownCoupons);
        if(!map.isEmpty()){
		    return map;
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }        
    }

    @PutMapping("/coupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    @Operation(summary = "Modify a coupon through his id, introducing all the parameters")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200", 
            description = "Comment modified succesfully", 
            content = {@Content(
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account", 
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Coupon not found, wrong id", 
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "User is not registered, wrong newUserEmail", 
            content = @Content
        ),
        @ApiResponse(
            responseCode = "501", 
            description = "Comment has not been modified", 
            content = @Content
        ),                 
    })
    public ResponseEntity<Object> modifyCoupon(@RequestParam("id") String id,
                                       @RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("newserEmail") String newUserEmail,
                                       @RequestParam("uses") int uses) {
        int status = couponsService.modifyCoupon(Long.parseLong(id), uses, Float.parseFloat(discount), code, newUserEmail);
        if(status==0){
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/coupons/coupon")).build();
        }else if(status==1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(status==2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }

    @Operation(summary = "Remove a coupon through his id")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200", 
            description = "Coupon removed succesfully", 
            content = {@Content(
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account", 
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Coupon not found, wrong id", 
            content = @Content
        ),        
})
    @DeleteMapping("/coupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeCoupon(@RequestParam(name="id") String id){
        if(couponsService.removeCoupon(Long.parseLong(id))){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a coupon introducing all the parameters")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200", 
            description = "Comment added succesfully", 
            content = {@Content(
                mediaType = "application/json"
            )}
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account", 
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "User is not registered, wrong userEmail", 
            content = @Content
        ),        
})
    @PostMapping("/coupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> createCoupon(@RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("usesRemaining") int uses,
                                       @RequestParam("userEmail") String user) {
        if(couponsService.createCoupon(uses, Integer.parseInt(discount), code, user)){
            return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(Tools.API_HEADER+"/coupons/coupon")).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}
