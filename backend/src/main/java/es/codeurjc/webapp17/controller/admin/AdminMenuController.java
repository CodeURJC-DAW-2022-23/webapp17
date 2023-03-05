package es.codeurjc.webapp17.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.model.Post;
import es.codeurjc.webapp17.repository.PostRepo;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminMenuController {

    @Autowired
    PostRepo postRepo;

    @GetMapping("/adminMenu")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public String menu(Model model, HttpServletRequest request) {
        List<Post> menus = postRepo.findByTitle("menu");
        if(!menus.isEmpty()){
            model.addAttribute("hasMenu", true);
            model.addAttribute("menu", menus.get(0));
        }
        return "admin/menu";
    }

    @PostMapping("/adminMenu")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity postMenu(Model model, @RequestParam(name = "content") String content, HttpServletRequest request) {
        Post menu = postRepo.findByTitle("menu").get(0);
        menu.setContent(content);
        postRepo.saveAndFlush(menu);
        return ResponseEntity.ok().build();
    }
}
