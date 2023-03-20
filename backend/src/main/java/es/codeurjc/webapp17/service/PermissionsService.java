package es.codeurjc.webapp17.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PermissionsService {

    public boolean isUserLoggedIn(HttpServletRequest request, UsersService usersService){
        return ((request.getUserPrincipal() != null) && usersService.getUser(request.getUserPrincipal().getName()) != null);
    }
    
}
