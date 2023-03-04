package es.codeurjc.webapp17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import es.codeurjc.webapp17.model.Booking;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookingController {
    
    @Autowired
    UsersService users;

    @GetMapping("/book")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String book(Model model) {
        return "dishes/book";
    }

    @GetMapping("/bible")
    @NeedsSecurity(role=Tools.Role.NONE)
    public String bible(Model model) {
        return "secrets/bible";
    }

    @PostMapping("/book")
    @NeedsSecurity(role=Tools.Role.AUTH)
    public Object addBook(Model model, HttpServletRequest request, 
    @RequestParam(name="numPeople", required = true)int num, @RequestParam(name="tlfNumber", required = true)String tlf,
    @RequestParam(name="date", required = true) String date, @RequestParam(name="hour", required = true) String hour) {
        UserProfile user = users.getUser(request.getUserPrincipal().getName());
        if(user != null && user.getBookings().size() <= 3){
            user.getBookings().add(new Booking(user, date+" "+hour, num, tlf));
            users.getUsers().saveAndFlush(user);
            return new ModelAndView("redirect:/profile");
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
