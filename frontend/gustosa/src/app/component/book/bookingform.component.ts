import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { BookingService } from "src/app/service/booking.service";
import { ProductsService } from "src/app/service/products.service";
import { SessionService } from "src/app/service/session.service";
import { UserService } from "src/app/service/user.service";

@Component({
    selector: 'bookingform',
    templateUrl: 'bookingform.component.html',
    styleUrls: []
})
export class BookingFormComponent {

    numPeople?:number;
    tlfNumber?:string;
    startDate?:Date;
    time?:string;

    constructor(private bookingService : BookingService, userService : UserService, sessionService : SessionService, router : Router){
        userService.isUserLoggedIn().subscribe((val)=>{
            if(!val){
                sessionService.updateProfile();
                router.navigateByUrl("/login");
            }
        });
    }

    sendBookingRequest(){
        this.bookingService.sendBookRequest(this.numPeople, 
            this.startDate?.toString(), this.time, this.tlfNumber).subscribe(()=>{
                alert("Reserva enviada!");
            });
    }
}