package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminClientsController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/adminUsers")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String clients(Model model, @RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        List<UserProfile> listUsers = usersService.getUsers();
        model.addAttribute("user", listUsers);
        model.addAttribute("hola", listUsers.get(0));
        return "admin/clients";
    }

    @PostMapping("/adminUsers/modifyUser")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> handleFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam(value = "bio", required = false) String bio,
                                       @RequestParam(value = "password", required = false) String password) {
        usersService.modifyUser(Long.parseLong(id), name, email, bio, password);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminUsers")).build();
    }

    @GetMapping("/adminUsers/removeUser")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> removeAction(@RequestParam(name="email") String email){
        usersService.removeUser(email);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminUsers")).build();
    }

    //TODO ADD NEW USER/ADMIN; CHECK SAME PASSWORD; MODIFY IMAGE; PAGINATION
        
}
