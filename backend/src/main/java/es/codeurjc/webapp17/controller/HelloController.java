package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.UsersRepo;
import jakarta.annotation.PostConstruct;

@Controller
public class HelloController {

    @Autowired
    private UsersRepo users;

    @PostConstruct
    public void init(){
        users.save(new UserProfile("Jhon Smith", "helloworld@email.com"));
    }

    private int count = 0;
    @GetMapping("/")
    public String handle(Model model) {
        model.addAttribute("message", users.findByEmail("helloworld@email.com").get(0).getName());
        count++;
        return "index";
    }

}

