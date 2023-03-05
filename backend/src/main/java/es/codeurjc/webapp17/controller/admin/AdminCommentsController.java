package es.codeurjc.webapp17.controller.admin;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.service.CommentsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminCommentsController {

    @Autowired
    CommentsService commentsService;

    @GetMapping("/adminComments")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String menu(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        List<Comment> commentsList = commentsService.getCommentsRepo().findAll();
        List<Comment> commentsShown = new ArrayList<>();
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)commentsList.size() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        Comment comment;
        for(int i=0; i<pageSize; i++){ 
            if(((page) * pageSize)+i<commentsList.size()){
                comment = commentsList.get(((page) * pageSize)+i);
                commentsShown.add(comment);
            }
        }
        model.addAttribute("comments", commentsShown);
        return "admin/comments";
    }

    @GetMapping("/adminComments/removeComment")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> removeAction(@RequestParam(name="id") String id){
        commentsService.removeComment(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminComments")).build();
    }
}
