package es.codeurjc.webapp17.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.codeurjc.webapp17.tools.Tools;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminClientsController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/adminUsers")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public String clients(Model model, @RequestParam(defaultValue = "0") int page) {
        List<UserProfile> listUsers = usersService.getUsers();
        List<UserProfile> shownUsers = new ArrayList<UserProfile>();
        int pageSize = 8;
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)listUsers.size() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        UserProfile user;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<listUsers.size()){
                user = listUsers.get(((page) * pageSize)+i);
                shownUsers.add(user);
            }
        }
        model.addAttribute("user", shownUsers);
        return "admin/clients";
    }

    @PostMapping("/adminUsers/modifyUser")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleEditFormSubmission(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam(value = "bio", required = false) String bio,
                                       @RequestParam(value = "password", required = false) String password) {
        usersService.modifyUser(Long.parseLong(id), name, email, bio, password);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminUsers")).build();
    }

    @GetMapping("/adminUsers/removeUser")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> removeAction(@RequestParam(name="email") String email){
        usersService.removeUser(email);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminUsers")).build();
    }

    @PostMapping("/adminUsers/createUser")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> handleCreationFormSubmissionAdmin(@RequestParam("role") String role,
                                       @RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam(value = "bio", required = false) String bio,
                                       @RequestParam("password") String password) throws IOException{
        Boolean admin = role.equals("Administrador");
        usersService.registerUserFromForm(name, password, email, bio, admin);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminUsers")).build();
    }      
}
