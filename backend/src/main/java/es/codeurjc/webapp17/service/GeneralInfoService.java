package es.codeurjc.webapp17.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Booking;
import es.codeurjc.webapp17.model.Post;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.BookingsRepo;
import es.codeurjc.webapp17.repository.PostRepo;

@Service
public class GeneralInfoService {
    
    @Autowired
    PostRepo postRepo;

    @Autowired
    BookingsRepo bookingsRepo;

    public Post getMenu(){
        return postRepo.findByTitle("menu").get(0);
    }


    public void setMenu(Map<String, Object> payload){
        Post menu = postRepo.findByTitle("menu").get(0);
        menu.setContent((String)payload.get("content"));
        postRepo.saveAndFlush(menu);
    }

    public void bookingApplyState(long id){
        Booking c = bookingsRepo.findById(id).get();
        c.setConfirmation(true);
        bookingsRepo.saveAndFlush(c);
    }

    public Boolean deleteBooking(long id){
        Optional<Booking> booking = getBookingsRepo().findById(id);
        if(!booking.isEmpty()){
            getBookingsRepo().delete(booking.get());
            return true;
        }else{
            return false;
        }
    }

    public BookingsRepo getBookingsRepo() {
        return bookingsRepo;
    }

}
