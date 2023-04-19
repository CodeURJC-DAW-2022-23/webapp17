package es.codeurjc.webapp17.controller.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.model.Coupon;
import es.codeurjc.webapp17.model.request.CouponsRequests.CreateCouponsRequest;
import es.codeurjc.webapp17.model.request.CouponsRequests.ModifyCouponsRequest;
import es.codeurjc.webapp17.service.CouponsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(Tools.API_HEADER+"/coupons")
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
	@GetMapping("")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public Object showCoupons(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Coupon> coupons = couponsService.getCouponsRepo().findAll(PageRequest.of(page, 8));
        if(!coupons.isEmpty()){
		    return coupons;
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }        
    }

    @PutMapping("/coupon/{id}")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    @Operation(summary = "Modify a coupon through his id, introducing all the parameters")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "202", 
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
    public ResponseEntity<Object> modifyCoupon(@PathVariable(name = "id") String id,
                                        @RequestBody ModifyCouponsRequest couponsRequest) {
        int status = couponsService.modifyCoupon(Long.parseLong(id), couponsRequest.getUses(), 
        Float.parseFloat(couponsRequest.getDiscount()), couponsRequest.getCode(), couponsRequest.getNewUserEmail());
        if(status==0){
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/coupons/" + id)).build();
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
    @DeleteMapping("/coupon/{id}")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeCoupon(@PathVariable(name="id") String id){
        if(couponsService.removeCoupon(Long.parseLong(id))){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create a coupon introducing all the parameters")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "201", 
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
    public ResponseEntity<Object> createCoupon(@RequestBody CreateCouponsRequest couponsRequest) {
        long newCoupon = couponsService.createCoupon(couponsRequest.getUses(),
        Integer.parseInt(couponsRequest.getDiscount()),
        couponsRequest.getCode(), couponsRequest.getUser());
        if(newCoupon != -1){
            return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(Tools.API_HEADER+"/coupons/" + newCoupon)).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}
