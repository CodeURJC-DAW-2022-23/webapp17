package es.codeurjc.webapp17.controller.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp17.service.GeneralInfoService;
import es.codeurjc.webapp17.service.PermissionsService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.webapp17.model.Booking;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.model.request.BookingRequests.ChangeBookingRequest;
import es.codeurjc.webapp17.model.request.BookingRequests.CreateBookingRequest;
import es.codeurjc.webapp17.service.UsersService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping(Tools.API_HEADER + "/bookings")
public class BookingsApiController {

    @Autowired
    GeneralInfoService generalInfoService;

    @Autowired
    UsersService usersService;

    @Autowired
    PermissionsService permissionsService;

    @Operation(summary = "Get bookings")
    @GetMapping("")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public Object getBookings(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        Page<Booking> bookingsPage = generalInfoService.getBookingsRepo().findAll(PageRequest.of(page, 8));
        return bookingsPage;
    }

    @PutMapping("/booking/{id}")
    @Operation(summary = "Change booking state")
    @ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Change Accepted", 
                content = @Content
                )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> changeBooking(Model model, @PathVariable(name = "id") String id, 
        HttpServletRequest request) {
        generalInfoService.bookingApplyState(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/bookings/booking")).build();
    }

    @DeleteMapping("/booking/{id}")
    @Operation(summary = "Remove a booking")
    @ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Removed succesfully", 
                content = @Content
                ),
        @ApiResponse(
                responseCode = "404", 
                description = "Booking Not Found", 
                content = @Content
                ),
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> deleteBooking(Model model, @PathVariable(name = "id") Long id, HttpServletRequest request) {
        if (generalInfoService.deleteBooking(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @PostMapping("/booking")
    @Operation(summary = "Add booking")
    @ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Change Accepted", 
                content = @Content
                )
    })
    @NeedsSecurity(role=Tools.Role.USER)
    public Object addBooking(Model model, HttpServletRequest request, 
    @RequestBody CreateBookingRequest bookingRequest) {
        if(permissionsService.isUserLoggedIn(request, usersService)){
            UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
            usersService.addBookingToUser(request.getUserPrincipal().getName(), new Booking(user, bookingRequest.getDate()
            +" "+bookingRequest.getHour(), bookingRequest.getNumPeople(), bookingRequest.getTlf()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/bookings/booking")).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }





}

