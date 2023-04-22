import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from 'src/app/model/booking.model';
import { Page } from 'src/app/model/pageable.model';
import { BookingService } from 'src/app/service/booking.service';


@Component({
  selector: 'admin',
  templateUrl: './bookings-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class BookingsAdminComponent {

    bookings: Observable<Page<Booking>>;
    currentPage = 1;
  
    constructor (private boookingService: BookingService){
      this.bookings = this.boookingService.getBookings(0);
    }

    onDelete(bookingId: string) {
      this.boookingService.deleteBooking(bookingId).subscribe(() => {
        this.bookings = this.boookingService.getBookings((this.currentPage - 1))
      });
    }

    onModifyStatus(bookingId: string) {
      this.boookingService.confirmBooking(bookingId).subscribe(() => {
        this.bookings = this.boookingService.getBookings((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
      }
      this.bookings = this.boookingService.getBookings((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.bookings = this.boookingService.getBookings(this.currentPage - 1)
    }
  
}