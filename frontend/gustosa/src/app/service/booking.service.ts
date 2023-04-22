import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, throwError } from "rxjs";
import { environment } from "../environment";
import { ApiResources } from "../apiresources";
import { Booking } from "../model/booking.model";
import { Page } from "../model/pageable.model";


@Injectable({ providedIn: 'root' })
export class BookingService {
    
    constructor(private httpClient: HttpClient){}

    sendBookRequest(numPeople?: number,
            date?: string,
            hour?: string,
            tlf?: string) : Observable<any>{
        var data = {
            numPeople: numPeople,
            date: date,
            hour: hour,
            tlf: tlf
        }
        let url = environment.apiUrl+"/"+ApiResources.Booking+"/";
        const err = new Error('Server error.');
        return this.httpClient.post(url, data, {withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

    getBookings(page?:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Bookings;
        const err = new Error('Server error.');
        var data = {};
        if(page != null)
            data = {
                "page": page
            }
        return this.httpClient.get(url, { params:data, withCredentials: true}).pipe(
            map(response => response as Page<Booking>),
            catchError(error => throwError(() => err))
        );
    }

    deleteBooking(id:string){
        let url = environment.apiUrl+"/"+ApiResources.Booking+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.delete(url, {withCredentials: true});
    }

    confirmBooking(id:string){
        let url = environment.apiUrl+"/"+ApiResources.Booking+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.put(url, {withCredentials: true});
    }

}