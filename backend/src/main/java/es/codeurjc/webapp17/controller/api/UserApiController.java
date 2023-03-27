package es.codeurjc.webapp17.controller.api;

import java.io.IOException;
import java.net.URI;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.webapp17.model.Image;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.PermissionsService;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(Tools.API_HEADER+"/users/")
public class UserApiController {

    @Autowired
    UsersService usersService;

	@Autowired
	PermissionsService permissionsService;


	//Image
	@GetMapping("/profileImage")
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
	@RequestParam long id) throws SQLException, IOException {
        ResponseEntity<Object> res = usersService.getUserImage(id);
        if (res != null) {
            return res;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }	
    }

	@PostMapping(value="/profileImage",
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
    public ResponseEntity<Object> uploadImage(@RequestParam long id, @RequestPart MultipartFile imageFile, HttpServletRequest request) throws SQLException, IOException {

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
	@JsonView(UserProfile.class)
    public @ResponseBody Object getUserInfo(HttpServletRequest request,
	@RequestParam(name="email", required = false) String email){
		if(permissionsService.canViewUsers(request, usersService)){
			Map<String,Object> ui = usersService.getUserInfo(email);
            if(ui != null){
                return ui;
            }
		}
        if(permissionsService.isUserLoggedIn(request, usersService)){
            Map<String,Object> ui = usersService.getUserInfo(request.getUserPrincipal().getName());
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
	@RequestParam(name="email", required = false) String email,
    @RequestParam(name="newName", required = false) String name, @RequestParam(name="newBio", required = false) String newBio,
	@RequestParam(name="newPassword", required = false) String newPassword){
        
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
    public ResponseEntity<Object> changeDescriptionPost(HttpServletRequest request, @RequestParam(name="email", required = false) String email) throws ServletException{
        HashMap<String, Object> map = new HashMap<>();
        if(permissionsService.isUserLoggedIn(request, usersService)){
			if(permissionsService.canEditUsers(request, usersService) && email != null){
				if(usersService.removeUser(email))
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
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleCreationFormSubmissionAdmin(@RequestParam("role") String role,
                                       @RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam(value = "bio", required = false) String bio,
                                       @RequestParam("password") String password) throws IOException{
        Boolean admin = role.equals("admin");
        usersService.registerUserFromForm(name, password, email, bio, admin);
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
	@GetMapping("/users")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Object getUsers(@RequestParam(defaultValue = "0") int pageNumber) {
		Page<UserProfile> page = usersService.getUsersRepo().findAll(PageRequest.of(pageNumber, 8));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", page);

		return map;
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
    public @ResponseBody Object login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
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
