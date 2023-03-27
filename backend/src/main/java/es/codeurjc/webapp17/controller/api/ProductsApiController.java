package es.codeurjc.webapp17.controller.api;

import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Object products(@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.productsPaginated(page, request);
		if (map.get("product")!=null){
			return map;
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/productImages")
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
    public ResponseEntity<Object> downloadImage(@RequestParam long id,@RequestParam int idImage) throws SQLException {
        ResponseEntity<Object> response = productsService.downloadImage(id, idImage);
        return response;
    }


    @PostMapping("/comment")
    @Operation(summary = "Add a comment on a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "201", 
					description = "Added comment on a product", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User is not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Error while adding comment on a product", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.USER)
    public ResponseEntity<Object> addComment(HttpServletRequest request, @RequestParam long id,@RequestParam(name = "content") String content, 
    @RequestParam(name = "stars") int stars) throws SQLException {
        ResponseEntity<Object> response = productsService.addComment(request, id, content, stars);
        return response;
    }

    @PostMapping("/cart")
    @Operation(summary = "Add a product to the cart")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Added product to the cart", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User is not logged in", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Error while adding product to the cart", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Object addToCart(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.addToCart(id, request);
		if (map.get("Login")=="true") {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
        return map;
    }

	@PutMapping("/product")
	@Operation(summary = "Modify the fields of a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Product modified succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found, wrong id", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not authorized, login with an admin account",
					content = @Content
							) 
	})
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> modifyProduct(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "tags", required = false) String tags,
                                       @RequestParam("price") Float price) {
	String[] tagsArray = null;
	if(tags != null){
		tagsArray = tags.split(", ");
	}
        if(productsService.modifyProduct(Long.parseLong(id), name, price, description, tagsArray)){
			return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/products/product")).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/product")
	@Operation(summary = "Remove a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Product removed succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found, wrong id", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not authorized, login with an admin account",
					content = @Content
							)
	})
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeProduct(@RequestParam(name="id") long id) {
        if(productsService.deleteProduct(id)){
			return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
    }

    @PostMapping("/product")
	@Operation(summary = "Create a new product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Product created succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not authorized, login with an admin account",
					content = @Content
							)
	})
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> addProduct(@RequestParam("name") String name,
                                       @RequestParam("price") String price,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "tags", required = false) String tags){
        
	String[] tagsArray = {""};
	if(tags != null){
		tagsArray = tags.split(", ");
	}
        productsService.addProduct(name, Float.parseFloat(price), description, tagsArray);
		return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/products/product")).build();

    }
    

	@GetMapping("/individualProduct")
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
    public @ResponseBody Object getIndividualProduct(@RequestParam(name="id") long id, HttpServletRequest request) {
        HashMap<String,Object> map = productsService.descriptionProduct(id, 0, request);
		if (map.size()!=0){
			return map;
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

	@DeleteMapping("/image")
	@Operation(summary = "Delete the image of a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "The image has been removed succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found, wrong id", 
					content = @Content
					),
			@ApiResponse(
            responseCode = "405", 
            description = "User not authorized, login with an admin account",
			content = @Content
        			)
	})
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeImage(@RequestParam(name="id") long id, HttpServletRequest request) {
        if(productsService.deleteImage(id)){
        	return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}
    }

    @PostMapping("/image")
	@Operation(summary = "Add an image to a product")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "The image has been added succesfully", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "400", 
					description = "Error uploading the image", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "Product not found, wrong id", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "405", 
					description = "User not authorized, login with an admin account",
					content = @Content
        			)
	})
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> uploadImage(@RequestParam(name="id") long id, @RequestParam MultipartFile imageFile, HttpServletRequest request){
        try{
			if(productsService.addImage(id, imageFile)){
				return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/products/image")).build();
			}else{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();			
			}
		}catch(Exception ex){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
    }
}
