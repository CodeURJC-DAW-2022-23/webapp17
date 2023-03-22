package es.codeurjc.webapp17.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.CommentsService;
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

    @GetMapping("/cart")
    @Operation(summary = "Get the actual cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Cart showed", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Cart not found", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public HashMap<String,Object> cart(Model model, HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);
        if (map.size()!=0) {
            return map;
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteItem/{id}")
    @Operation(summary = "Deletes an item from cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Item deleted", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Item cannot be deleted", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public void deleteItem(@PathVariable long id, HttpServletRequest request){
        cartsService.deleteItem(id, request);
    }

    @GetMapping("/decreaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public void decreaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.decreaseQuantity(id, request);
    }

    @GetMapping("/increaseQuantity/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public void increaseQuantity(@PathVariable long id, HttpServletRequest request){
        cartsService.increaseQuantity(id, request);
    }
    
    @GetMapping("/checkout")
    @Operation(summary = "Get the actual checkout form")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Checkout form showed", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Checkout form not found", 
					content = @Content
					)	
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public HashMap<String,Object> checkout(Model model, HttpServletRequest request) {
        HashMap<String,Object> map = cartsService.cartAndCheckout(request);
        if (map.size()!=0) {
            return map;
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

}
