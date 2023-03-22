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
import es.codeurjc.webapp17.model.UserProfile;
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
@RequestMapping(Tools.API_HEADER + "/products/")
public class ProductsApiController {

    @Autowired
    ProductsService productsService;

    @Autowired
    UsersService usersService;
    
    @GetMapping("/products")
    @Operation(summary = "Get list of products paginated")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found the Products", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Products not found", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.NONE)
    public Map<String,Object> products(Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        
        Map<String,Object> map = productsService.products(page);

        //Recommended Products To Map
        if(request.getUserPrincipal() != null){
            UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
            if(user != null && !user.getOrders().isEmpty()){
                List<Long> recomendedProducts = usersService.getUsersRepo()
                    .getRecomendedByProductList(user.getOrders().get(Math.max(user.getOrders().size()-2, 0)).getId());
                List<Product> products = productsService.getProductsRepo().findAllById(recomendedProducts);
                if(products.size() > 0){
                    model.addAttribute("recomended_product", products.subList(0, Math.min(products.size(), 4)));
                    model.addAttribute("has_recomended", true);
                }
            }
        }

        return map;
    }


}
