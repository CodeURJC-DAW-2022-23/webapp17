package es.codeurjc.webapp17.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
                description = "User not authorized, login with an admin account"
            )
	})
	@GetMapping("/coupons")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Map<String,Object> coupons(Model model, @RequestParam(defaultValue = "0") int page) {
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
		return map;        
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
            responseCode = "403", 
            description = "User not authorized, login with an admin account"
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "Coupon not found or bad parameters"
        ),        
    })
    public void handleEditFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("newserEmail") String newUserEmail,
                                       @RequestParam("uses") int uses) {
        couponsService.modifyCoupon(Long.parseLong(id), uses, Float.parseFloat(discount), code, newUserEmail);
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
            responseCode = "403", 
            description = "User not authorized, login with an admin account"
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "Coupon not found, wrong id"
        ),        
})
    @DeleteMapping("/coupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void removeAction(@RequestParam(name="id") String id){
        couponsService.removeCoupon(Long.parseLong(id));
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
            responseCode = "403", 
            description = "User not authorized, login with an admin account"
        ),
        @ApiResponse(
            responseCode = "405", 
            description = "Bad parameters, check if the user exists"
        ),        
})
    @PostMapping("/coupon")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void handleCreationFormSubmission(@RequestParam("code") String code,
                                       @RequestParam("discount") String discount,
                                       @RequestParam("usesRemaining") int uses,
                                       @RequestParam("userEmail") String user) {
        couponsService.createCoupon(uses, Integer.parseInt(discount), code, user);
    }
    
}
