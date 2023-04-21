import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, throwError } from "rxjs";
import { environment } from "../environment";
import { ApiResources } from "../apiresources";


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
        let url = environment.apiUrl+"/"+ApiResources.Booking;
        const err = new Error('Server error.');
        return this.httpClient.post(url, data, {withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }
}