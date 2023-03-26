package es.codeurjc.webapp17.controller.api;


import java.util.HashMap;
import java.util.Map;
import es.codeurjc.webapp17.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(Tools.API_HEADER + "/orders/")
public class OrdersApiController {

        @Autowired
        CartsService cartsService;

    @GetMapping("/orders")
    @Operation(summary = "View orders")
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
            responseCode = "403", 
            description = "No permission to  see the orders", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Map<String,Object> viewOrdersPaginated(HttpServletRequest request, @RequestParam(defaultValue = "0") int page){
        HashMap<String, Object> map = new HashMap<>();
        int pageSize = 8;
        Page<Cart> carts = cartsService.getCartsRepo().findByStatusNot(Cart.STATUS_NEW, PageRequest.of(page, pageSize));             
        if(!carts.isEmpty()){
            map.put("hasCarts", true);
            map.put("orders", carts);
        }
        return map;
    };


    @PutMapping("/orderStatus")
    @Operation(summary = "Change the status of an order")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Found the order", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "405", 
                description = "Order not found", 
                content = @Content
                ),
        @ApiResponse(
            responseCode = "403", 
            description = "No permission to  change the status of the order", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public  void changeState(@RequestParam(name = "id") Long id) {
        cartsService.changeOrderState(id);
          
};

@DeleteMapping("/order")
    @Operation(summary = "Change state of an order")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Found the order", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "405", 
                description = "Order not found", 
                content = @Content
                ),
        @ApiResponse(
            responseCode = "403", 
            description = "No permission to  change the status of the order", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void deleteOrder(@RequestParam(name = "id") Long id) {
        cartsService.getCartsRepo().deleteById(id);          
};
}
