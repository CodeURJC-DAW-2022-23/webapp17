package es.codeurjc.webapp17.controller.admin;

import java.net.URI;
import java.util.List;

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

import es.codeurjc.webapp17.model.Booking;
import es.codeurjc.webapp17.model.Cart;
import es.codeurjc.webapp17.model.Post;
import es.codeurjc.webapp17.repository.BookingsRepo;
import es.codeurjc.webapp17.repository.CartsRepo;
import es.codeurjc.webapp17.repository.PostRepo;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminBookingController {

    @Autowired
    BookingsRepo bookingsRepo;

    @GetMapping("/adminBookings")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String menu(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Page<Booking> carts = bookingsRepo.findAll(PageRequest.of(page, pageSize));
        model.addAttribute("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)carts.getSize() / (float)pageSize);
        model.addAttribute("nextPag", (int)Math.min(page+1, num-1));
        
        
        if(!carts.isEmpty()){
            model.addAttribute("hasCarts", true);
            model.addAttribute("books", carts);
        }
        return "admin/book";
    }

    @PostMapping("/adminBookings")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity postMenu(Model model, @RequestParam(name = "id") Long id, 
        @RequestParam(name = "action") int action, HttpServletRequest request) {
        if(action==1){
            bookingsRepo.deleteById(id);
        }else{
            Booking c = bookingsRepo.findById(id).get();
            c.setConfirmation(true);
            bookingsRepo.saveAndFlush(c);
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/adminBookings")).build();
    }
}
