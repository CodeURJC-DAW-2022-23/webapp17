package es.codeurjc.webapp17.controller.api;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.webapp17.model.Booking;
import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.request.UsersRequests;
import es.codeurjc.webapp17.model.request.UsersRequests.CreateUserRequest;
import es.codeurjc.webapp17.model.request.UsersRequests.LoginRequest;
import es.codeurjc.webapp17.model.request.UsersRequests.ModifyUserRequest;
import es.codeurjc.webapp17.model.request.UsersRequests.UserInfoRequest;
import es.codeurjc.webapp17.service.CartsService;
import es.codeurjc.webapp17.service.PermissionsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(Tools.API_HEADER+"/users")
public class UserApiController {

    @Autowired
    UsersService usersService;

	@Autowired
    CartsService cartsService;

	@Autowired
	PermissionsService permissionsService;


	//Image
	@GetMapping("/{id}/image")
    @Operation(summary = "Get user image")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found the Image", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "User not found", 
					content = @Content
					) 
	})
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> downloadImage(@Parameter(description = "id of the user") 
	@PathVariable long id) throws SQLException, IOException {
        ResponseEntity<Object> res = usersService.getUserImage(id);
        if (res != null) {
            return res;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }	
    }

	@PostMapping(value="/{id}/image",
	consumes = {
		"multipart/form-data"
	})
    @NeedsSecurity(role=Tools.Role.NONE)
	@Operation(summary = "Add user profile picture")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Updated profile picture", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "User not found", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to edit user info", 
				content = @Content
				) 		
	})
    public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestPart MultipartFile imageFile, HttpServletRequest request) throws SQLException, IOException {

		if((permissionsService.isUserLoggedIn(request, usersService) && usersService.getUser(request.getUserPrincipal().getName()).getID().equals(id))
			|| permissionsService.canEditUsers(request, usersService)){
			usersService.changeImage(usersService.getUser(id).getEmail(), BlobProxy.generateProxy(
			imageFile.getInputStream(), imageFile.getSize()));
			return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(Tools.API_HEADER+"/users/profileImage")).build();
        }
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

	@GetMapping("/user")
    @NeedsSecurity(role=Tools.Role.USER)
	@Operation(summary = "Get user info, if user not specified returns current user")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found the user", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
					responseCode = "404", 
					description = "User not found", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to see user info", 
				content = @Content
				) 		
	})
    public @ResponseBody Object getUserInfo(HttpServletRequest request,
	@RequestParam(required = false, name = "email") String email){
		if(email != null && permissionsService.canViewUsers(request, usersService)){
			UserProfile ui = usersService.getUser(email);
            if(ui != null){
                return ui;
            }
		}else if(permissionsService.isUserLoggedIn(request, usersService)){
            UserProfile ui = usersService.getUser(request.getUserPrincipal().getName());
            if(ui != null){
                return ui;
            }
        }
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

	@PutMapping("/user")
	@NeedsSecurity(role = Tools.Role.USER)
	@Operation(summary = "Modify user, if user not specified modifies current user")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Updated info", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
					responseCode = "404", 
					description = "User not found", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to edit user", 
				content = @Content
				) 		
	})
    public ResponseEntity<Object> modifyUser(HttpServletRequest request, 
	@RequestBody ModifyUserRequest user){
        String email = user.getEmail();
		String name = user.getName();
		String newBio = user.getNewBio();
		String newPassword = user.getNewPassword();
		String emailToChange = "";
		if(permissionsService.canEditUsers(request, usersService) && email != null){
			emailToChange = email;
		}else if(permissionsService.isUserLoggedIn(request, usersService)){
			emailToChange = request.getUserPrincipal().getName();
		}

		if(emailToChange != null){	
            if(name != null){
				if(!usersService.changeName(emailToChange, name))
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			if(newBio != null){
				if(!usersService.changeDescription(emailToChange, newBio))
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			if(newPassword != null){
				if(!usersService.changePassword(emailToChange, newPassword))
					return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/users/user")).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


	@Operation(summary = "Remove user, if email not specified remove current user")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "User Removed", 
					content = @Content
					),
			@ApiResponse(
					responseCode = "404", 
					description = "User not found", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to edit user", 
				content = @Content
				) 		
	})
	@DeleteMapping("/user")
    @NeedsSecurity(role = Tools.Role.USER)
    public ResponseEntity<Object> changeDescriptionPost(HttpServletRequest request, @RequestBody(required = false) UserInfoRequest email) throws ServletException{
        if(permissionsService.isUserLoggedIn(request, usersService)){
			if(permissionsService.canEditUsers(request, usersService) && email.getEmail() != null){
				if(usersService.removeUser(email.getEmail()))
					return ResponseEntity.ok().build();
				else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
            usersService.removeUser(request.getUserPrincipal().getName());
            request.logout();
			return ResponseEntity.ok().build();
        }
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

	@Operation(summary = "Create user")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "User Added", 
					content = @Content
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to edit user", 
				content = @Content
				) 		
	})
	@PostMapping("/user")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest user, HttpServletRequest request) throws IOException{
        Boolean admin = false;
		if(user.getRole() != null)
			admin = user.getRole().equals("admin") && permissionsService.canEditUsers(request, usersService);
        usersService.registerUserFromForm(user.getName(), user.getPassword(), user.getEmail(), user.getBio(), admin);
		try{
        	if(!permissionsService.isUserLoggedIn(request, usersService)) request.login(user.getEmail(), user.getPassword());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(Tools.API_HEADER+"/users/user")).build();
    }


	@Operation(summary = "Get users info")
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
	@GetMapping("/orders")
    @NeedsSecurity(role=Tools.Role.USER)
    public @ResponseBody Object getUserOrders(@RequestParam(defaultValue = "0") int pageNumber, HttpServletRequest request) {
		if(permissionsService.isUserLoggedIn(request, usersService)){
			UserProfile u = usersService.getUser(request.getUserPrincipal().getName());
			Page<Cart> page = cartsService.getUserOrders(u, pageNumber, 2);
			return page;
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}


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
	@GetMapping("/orders/{id}")
    @NeedsSecurity(role=Tools.Role.USER)
    public @ResponseBody Object getUserOrder(@PathVariable long id, HttpServletRequest request) {
		if(permissionsService.isUserLoggedIn(request, usersService)){
			UserProfile u = usersService.getUser(request.getUserPrincipal().getName());
			Cart c = u.getOrders().stream().filter(n -> n.getId() == id).findFirst().get();
			c.totalPrice();
			return c;
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@Operation(summary = "Get users info")
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
	@GetMapping("")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Object getUsers(@RequestParam(defaultValue = "0") int pageNumber) {
		if(pageNumber == -1){
			return usersService.getUsersRepo().findAll();
		}else{
			Page<UserProfile> page = usersService.getUsersRepo().findAll(PageRequest.of(pageNumber, 8));
			return page;
		}
	}

	@Operation(summary = "Login")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Ok", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
				responseCode = "403", 
				description = "Wrong auth", 
				content = @Content
				) 		
	})
	@GetMapping("/login")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Object login(HttpServletRequest request, @RequestParam(name="email") String email, 
	@RequestParam(name="password") String password) {
		try {
			request.login(email, password);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	@Operation(summary = "Logout")
	@GetMapping("/logout")
    @NeedsSecurity(role=Tools.Role.NONE)
    public @ResponseBody Object logout(HttpServletRequest request) {
		try {
			request.logout();
			return ResponseEntity.ok().build();
		} catch (ServletException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
    
}
