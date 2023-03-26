package es.codeurjc.webapp17.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.service.AdminService;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.service.ProductsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;



    @RestController
    @RequestMapping(Tools.API_HEADER + "/admin/")
public class AdminApiController {


    @Autowired
    AdminService adminService;

    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    CartsService cartsService;
    
    @GetMapping("/statistics")
    @Operation(summary = "See the statistics")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Found the statistic", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "404", 
                description = "Statistics not found", 
                content = @Content
                ),
        @ApiResponse(
            responseCode = "403", 
            description = "No permission to see the statistics", 
            content = @Content
            )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public  @ResponseBody Map<String,Object> getStatistics(){
        HashMap<String, Object> map = new HashMap<>();

        List<Long[]> productsQuery = adminService.getProductsRepo().getSales();

        List<Product> topProducts = productsQuery.stream().map(e -> adminService.getProductsRepo().findById(e[0]).get()).toList();
        List<Long> topSales = productsQuery.stream().map(e -> e[1]).toList();
        boolean salesExist = false;
        map.put("topProducts", topProducts);
        map.put("topSales", topSales);
        if (!topProducts.isEmpty()){
            salesExist = true;
            map.put("salesExist", salesExist);
        }
        map.put("users", usersService.getUsersRepo().getTotalUsers());
        map.put("products", productsService.getProductsRepo().getTotalProducts());
        map.put("ratingAVG", commentsService.getCommentsRepo().getAvgRating());
        map.put("totalComments", commentsService.getCommentsRepo().getTotalComments());
        map.put("finishedOrders", cartsService.getCartsRepo().getFinishedOrders());
        map.put("processOrders", cartsService.getCartsRepo().getInProcessOrders());
        map.put("cartOrders", cartsService.getCartsRepo().getFullCarts());

        return map;
    };
}
