package es.codeurjc.webapp17.controller;

import java.util.Enumeration;
import java.util.Iterator;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nimbusds.oauth2.sdk.Response;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ExceptionController implements ErrorController {

    @GetMapping("/error")
    public Object errorHandler(HttpServletRequest request, Model model) {
        String in =  (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        // Obtain status code
        Integer statusCode = ((Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        if (in.startsWith("/api")) {
            return ResponseEntity.status(statusCode).build();
        }
        
        String message = "Unknown error.";
        if(statusCode != null)
            switch(statusCode){
                case 404:
                    message="Error 404 not found";
            };
        model.addAttribute("message", message);
        return "errors/notfound";
    }

    public String getErrorPath() {
        return "/error";
    }
}
