package es.codeurjc.webapp17.controller.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.codeurjc.webapp17.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import es.codeurjc.webapp17.repository.CartsRepo;
import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.service.CommentsService;
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
        CartsRepo cartsRepo;

    @GetMapping("/viewOrders")
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
    public @ResponseBody Map<String,Object> viewOrdersPaginated(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page){
        HashMap<String, Object> map = new HashMap<>();
        int pageSize = 8;
        Page<Cart> carts = cartsRepo.findByStatusNot(Cart.STATUS_NEW, PageRequest.of(page, pageSize));             
        if(!carts.isEmpty()){
            map.put("hasCarts", true);
            map.put("orders", carts);
        }
        return map;
    };


    @GetMapping("/changeStateOrder")
    @Operation(summary = "Change state of an order")
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
            description = "No permission to  change the state of the order", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public  ResponseEntity changeState(Model model, @RequestParam(name = "id") Long id,@RequestParam(name = "action") int action, HttpServletRequest request) {
        if(action==1){
                cartsRepo.deleteById(id);
            }else{
                Cart c = cartsRepo.findById(id).get();
                c.setStatus(Cart.STATUS_DONE);
                cartsRepo.saveAndFlush(c);
            }
            return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminOrders")).build();
};
}
