package es.codeurjc.webapp17.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp17.model.Post;
import es.codeurjc.webapp17.repository.PostRepo;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@Controller
public class HelloController {

    @Autowired
    UsersService usersService;

    @Autowired
    PostRepo postRepo;

    @GetMapping("/")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String index(Model model) {
        model.addAttribute("message", usersService.getUsersRepo().findByEmail("test@example.com"));
        model.addAttribute("userName", "Pedro");
        return "index";
    }

    @GetMapping("/info")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String info(Model model) {
        return "info/info";
    }

    @GetMapping("/menu")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String menu(Model model) {
        List<Post> menus = postRepo.findByTitle("menu");
        if(!menus.isEmpty()){
            model.addAttribute("hasMenu", true);
            model.addAttribute("menu", menus.get(0));
        }
        return "menu/menu";
    }

}

