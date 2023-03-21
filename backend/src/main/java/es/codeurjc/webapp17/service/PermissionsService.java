package es.codeurjc.webapp17.service;

import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.tools.Tools.Role;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PermissionsService {

    public boolean isUserLoggedIn(HttpServletRequest request, UsersService usersService){
        return ((request.getUserPrincipal() != null) && usersService.getUser(request.getUserPrincipal().getName()) != null);
    }

    public boolean canViewUsers(HttpServletRequest request, UsersService usersService){
        return (isUserLoggedIn(request, usersService)) && 
            usersService.getUser(request.getUserPrincipal().getName()).hasRole(Role.ADMIN);
    }

    public boolean canEditUsers(HttpServletRequest request, UsersService usersService){
        return (isUserLoggedIn(request, usersService)) && 
            usersService.getUser(request.getUserPrincipal().getName()).hasRole(Role.ADMIN);
    }
    
}
