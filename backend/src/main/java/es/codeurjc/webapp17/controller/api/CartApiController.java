package es.codeurjc.webapp17.controller.api;


import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.model.request.CouponsRequests.GetCouponRequest;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.PermissionsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Tools.API_HEADER + "/carts/")
public class CartApiController {

    @Autowired
    CartsService cartsService;
	
	@Autowired
    UsersService usersService;

    @Autowired
    PermissionsService permissionsService;



    @GetMapping("/")
    @Operation(summary = "Get the actual cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Cart showed", 
					content = @Content
					),
				@ApiResponse(
					responseCode = "403", 
					description = "User not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Cart not found or empty", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public Object cart(HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);
        if (map.containsKey("notLogged")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		else if (map.size()!=0) {
            return map;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @DeleteMapping("/item/{id}")
    @Operation(summary = "Deletes an item from cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Item deleted", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Item cannot be deleted", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public Object deleteItem(@PathVariable long id, HttpServletRequest request){
        try {
			if (!permissionsService.isUserLoggedIn(request, usersService)) {
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
			}
			cartsService.deleteItem(id, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/item")).build();
		} catch (Exception ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
    }



    @PutMapping("/lessQuantity/{id}")
    @Operation(summary = "Decrease the quantity of a cart product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Decreased Succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Cannot decrease quantity", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public Object decreaseQuantity(@PathVariable long id, HttpServletRequest request){
    	try {
			if (!permissionsService.isUserLoggedIn(request, usersService)) {
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
			}
			cartsService.decreaseQuantity(id, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/lessQuantity")).build();
		} catch (Exception ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
    }



    @PutMapping("/moreQuantity/{id}")
    @Operation(summary = "Increase the quantity of a cart product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Increased Succesfully", 
					content = @Content
					),
			@ApiResponse(
						responseCode = "405", 
						description = "User not logged in", 
						content = @Content
						),
			@ApiResponse(
					responseCode = "404", 
					description = "Cannot increase quantity", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public Object increaseQuantity(@PathVariable long id, HttpServletRequest request){
    	try {
			if (!permissionsService.isUserLoggedIn(request, usersService)) {
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
			}
			cartsService.increaseQuantity(id, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/carts/moreQuantity")).build();
		} catch (Exception ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
    }
    


    @GetMapping("/finalCart")
    @Operation(summary = "Get the actual checkout form")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Checkout form showed", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "403", 
					description = "User not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Checkout form not found or empty cart", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public Object checkout(HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);
		if (map.containsKey("notLogged")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
        if (map.size()!=0) {
            return map;
        }
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/finalCart")
    @Operation(summary = "Finalize the checkout")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Checkout form sent correctly", 
					content = @Content
					),
			@ApiResponse(
						responseCode = "405", 
						description = "User not logged in", 
						content = @Content
						),
			@ApiResponse(
					responseCode = "404", 
					description = "Checkout form not sent correctly (maybe empty cart)", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> doCheckout(HttpServletRequest request) {
		if (!permissionsService.isUserLoggedIn(request, usersService)) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
		HashMap<String,Object> map = cartsService.cartAndCheckout(request);
		if (map.size()!=0) {
            ResponseEntity<Object> response = cartsService.doCheckout(request);
        	return response;
        }
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 
    }



    @PostMapping("/coupon")
    @Operation(summary = "Redeem a Coupon")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Redeemed coupon successfully", 
					content = @Content
					),
			@ApiResponse(
						responseCode = "405", 
						description = "User not logged in", 
						content = @Content
						),
			@ApiResponse(
					responseCode = "404", 
					description = "Cannot redeem coupon", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> redeem(@RequestBody GetCouponRequest code, HttpServletRequest request) {
		if (!permissionsService.isUserLoggedIn(request, usersService)) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
        ResponseEntity<Object> response = cartsService.redeem(code.getCode(), request);
        return response;
    }



    @PostMapping("/couponFree")
    @Operation(summary = "Unredeem a Coupon")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "202", 
					description = "Unredeemed coupon successfully", 
					content = @Content
					),
			@ApiResponse(
						responseCode = "405", 
						description = "User not logged in", 
						content = @Content
						),
			@ApiResponse(
					responseCode = "404", 
					description = "Cannot unredeem coupon", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> unredeem(HttpServletRequest request) {
		if (!permissionsService.isUserLoggedIn(request, usersService)) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
        ResponseEntity<Object> response = cartsService.unredeem(request);
        return response;
    }


}
