package es.codeurjc.webapp17.controller.api;

import es.codeurjc.webapp17.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping(Tools.API_HEADER + "/orders")
public class OrdersApiController {

        @Autowired
        CartsService cartsService;

    @GetMapping("")
    @Operation(summary = "View orders")
    @ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Orders listed", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "404", 
                description = "Orders not found", 
                content = @Content  
                ),
        @ApiResponse(
            responseCode = "403", 
            description = "User not authorized, login with an admin account", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public Object viewOrdersPaginated(HttpServletRequest request, @RequestParam(defaultValue = "0") int page){
        int pageSize = 8;
        Page<Cart> orderPage = cartsService.getCartsRepo().findByStatusNot(Cart.STATUS_NEW, PageRequest.of(page, pageSize));             
        return orderPage;
    };


    @PutMapping("/{id}")
    @Operation(summary = "Change the status of an order")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Order status changed succesfully", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "404", 
                description = "Order not found", 
                content = @Content
                ),
        @ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> changeState(@PathVariable(name = "id") Long id) {
        if(cartsService.changeOrderState(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
          
};

@DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Found the order", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "404", 
                description = "Order not found", 
                content = @Content
                ),
        @ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> deleteOrder(@PathVariable(name = "id") Long id) {
        if(cartsService.deleteOrder(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
           
};


@Operation(summary = "Get users order")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Ok", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Page not found", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission", 
				content = @Content
				) 		
	})
	@GetMapping("/{id}")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Object getUserOrder(@PathVariable long id, HttpServletRequest request) {
		return cartsService.getCartsRepo().findById(id).get(0);
	}
}
