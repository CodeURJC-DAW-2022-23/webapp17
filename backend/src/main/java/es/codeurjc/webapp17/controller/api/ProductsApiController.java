package es.codeurjc.webapp17.controller.api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.service.ProductsService;
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
        Map<String,Object> map = productsService.productsPaginated(page, request);
		if (map!=null){
			return map;
		}
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/{id}/image/{idImage}")
    @Operation(summary = "Get list of images from a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found images of the product", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Error showing images of product", 
					content = @Content
					) 
	})
    public ResponseEntity<Object> downloadImage(@PathVariable long id,@PathVariable int idImage) throws SQLException {
        ResponseEntity<Object> response = productsService.downloadImage(id, idImage);
        return response;
    }


    @PostMapping("/products/{id}/addComment")
    @Operation(summary = "Add a comment on a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Added comment on a product", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Error while adding comment on a product", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> addComment(HttpServletRequest request, @PathVariable long id,@RequestParam(name = "content") String content, 
    @RequestParam(name = "stars") int stars) throws SQLException {
        ResponseEntity<Object> response = productsService.addComment(request, id, content, stars);
        return response;
    }

    @PostMapping("/addToCart")
    @Operation(summary = "Add a product to the cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Added product to the cart", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Error while adding product to the cart", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> addToCart(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.addToCart(id, request);
        return map;
    }

	@PutMapping("/modifyProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void handleFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "tags", required = false) String tags,
                                       @RequestParam("price") Float price) {
	String[] tagsArray = null;
	if(tags != null){
		tagsArray = tags.split(", ");
	}
        productsService.modifyProduct(Long.parseLong(id), name, price, description, tagsArray);
    }

    @DeleteMapping("/removeProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void removeAction(@RequestParam(name="id") long id) {
        productsService.deleteProduct(id);
    }

    @PostMapping("/addProduct")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void handleCreationFormSubmissionAdmin(@RequestParam("name") String name,
                                       @RequestParam("price") String price,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "tags", required = false) String tags){
        
	String[] tagsArray = {""};
	if(tags != null){
		tagsArray = tags.split(", ");
	}
        productsService.addProduct(name, Float.parseFloat(price), description, tagsArray);
    }
    

	@GetMapping("/getIndividualProduct")
    @Operation(summary = "Get the description of a product (individual)")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Showed the information of the product", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Cannot show the information of the product", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Map<String,Object> getIndividualProduct(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.descriptionProduct(id, 0, request);
        return map;
    }
}
