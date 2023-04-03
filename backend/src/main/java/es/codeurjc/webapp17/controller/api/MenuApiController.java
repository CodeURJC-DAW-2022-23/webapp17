package es.codeurjc.webapp17.controller.api;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.service.GeneralInfoService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping(Tools.API_HEADER+"/menu/")
public class MenuApiController {
    
    @Autowired
    GeneralInfoService generalInfoService;


    @GetMapping("/")
    @NeedsSecurity(role=Tools.Role.NONE)
    @Operation(summary = "Get menu")
	@ApiResponses(value = { 
			@ApiResponse(
					responseCode = "200", 
					description = "Found the user", 
					content = {@Content(
							mediaType = "application/json"
							)}
					),
			@ApiResponse(
				responseCode = "403", 
				description = "No permission to view", 
				content = @Content
				) 		
	})
    public Object menu(HttpServletRequest request) {
        return generalInfoService.getMenu();
    }

    @PutMapping("/")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    @Operation(summary = "Modify menu")
	@ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Found the user", 
                content = {@Content(
                        mediaType = "application/json"
                        )}
                ),
        @ApiResponse(
            responseCode = "403", 
            description = "No permission to edit", 
            content = @Content
            ) 		
    })
    public ResponseEntity<Object> postMenu(HttpServletRequest request, @RequestBody Map<String, Object> payload) {
        generalInfoService.setMenu(payload);
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/menu")).build();
    }
}
