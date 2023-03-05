package es.codeurjc.webapp17.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ExceptionController implements ErrorController {

    @GetMapping("/error")
    public String errorHandler(HttpServletRequest request, Model model) {
        // Obtain status code
    Integer statusCode = ((Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
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
