package es.codeurjc.webapp17.controller.api;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import es.codeurjc.webapp17.service.UsersService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping(Tools.API_HEADER + "/bookings/")
public class BookingsApiController {

    @Autowired
    GeneralInfoService generalInfoService;

    @Autowired
    UsersService usersService;

    @Autowired
    PermissionsService permissionsService;

    @Operation(summary = "Get bookings")
    @GetMapping("/bookings")
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public HashMap<String,Object> getBookings(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        HashMap<String,Object> map = new HashMap<>();
        int pageSize = 8;
        Page<Booking> carts = generalInfoService.getBookingsRepo().findAll(PageRequest.of(page, pageSize));
        map.put("prevPag", (int)Math.max(0, page-1));
        int num = (int)Math.ceil((float)carts.getSize() / (float)pageSize);
        map.put("nextPag", (int)Math.min(page+1, num-1));
        
        if(!carts.isEmpty()){
            map.put("hasCarts", true);
            map.put("books", carts);
        }
        
        return map;
    }

    @PutMapping("/bookings")
    @Operation(summary = "Change booking state")
    @ApiResponses(value = { 
        @ApiResponse(
                responseCode = "200", 
                description = "Change Accepted", 
                content = @Content
                )
    })
    @NeedsSecurity(role=Tools.Role.ADMIN)
    public ResponseEntity<Object> changeBooking(Model model, @RequestParam(name = "id") Long id, 
        @RequestParam(name = "action") int action, HttpServletRequest request) {
        generalInfoService.bookingApplyState(id, action);
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/bookings/booking")).build();
    }

    @PostMapping("/bookings")
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
    @RequestParam(name="numPeople", required = true)int num, @RequestParam(name="tlfNumber", required = true)String tlf,
    @RequestParam(name="date", required = true) String date, @RequestParam(name="hour", required = true) String hour) {
        if(permissionsService.isUserLoggedIn(request, usersService)){
            usersService.addBookingToUser(request.getUserPrincipal().getName(), new Booking(null, date+" "+hour, num, tlf));
            return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/bookings/booking")).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }





}

