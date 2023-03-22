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
    @RequestMapping(Tools.API_HEADER + "/admin/")
public class AdminApiController {




    @GetMapping("/getStatistics")
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
    public void getStatistics(){

    };
}
