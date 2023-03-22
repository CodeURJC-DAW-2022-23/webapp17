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
import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Tools.API_HEADER + "/comments/")
public class CommentsApiController {

    @Autowired
    CommentsService commentsService;

    @Operation(summary = "Get comments of everyone sorted by pages of 8")
	@ApiResponses(value = { 
			@ApiResponse(
				responseCode = "200", 
				description = "Comments loaded succesfully", 
				content = {@Content(
					mediaType = "application/json"
				)}
			),
            @ApiResponse(
                responseCode = "403", 
                description = "User not authorized, login with an admin account"
            )
	})
	@GetMapping("/getComments")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public @ResponseBody Map<String,Object> menu(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
		HashMap<String, Object> map = new HashMap<>();
        int pageSize = 4;
        List<Comment> commentsList = commentsService.getCommentsRepo().findAll();
        List<Comment> commentsShown = new ArrayList<>();
        Comment comment;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<commentsList.size()){
                comment = commentsList.get(((page) * pageSize)+i);
                commentsShown.add(comment);
            }
        }
        map.put("comments", commentsShown);
        return map;
    }

    @Operation(summary = "Remove a comment through his id")
    @ApiResponses(value = { 
			@ApiResponse(
			    responseCode = "200", 
				description = "Comment removed succesfully", 
				content = {@Content(
					mediaType = "application/json"
				)}
			),
            @ApiResponse(
				responseCode = "403", 
				description = "User not authorized, login with an admin account"
			),
            @ApiResponse(
				responseCode = "404", 
				description = "Comment not found, wrong id"
			),        
	})
	@DeleteMapping("/removeComment")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public void removeAction(@RequestParam(name="id") String id){
        commentsService.removeComment(Long.parseLong(id));
    }
    
}
