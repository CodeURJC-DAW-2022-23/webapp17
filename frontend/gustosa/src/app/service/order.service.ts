import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, throwError } from "rxjs";
import { environment } from "../environment";
import { ApiResources } from "../apiresources";
import { Booking } from "../model/booking.model";
import { Page } from "../model/pageable.model";


@Injectable({ providedIn: 'root' })
export class OrderService {
    
    constructor(private httpClient: HttpClient){}

    getOrders(page?:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.OrdersAdmin;
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

    deleteOrder(id:string){
        let url = environment.apiUrl+"/"+ApiResources.OrderAdmin+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.delete(url, {withCredentials: true});
    }

    orderDone(id:string){
        let url = environment.apiUrl+"/"+ApiResources.OrderAdmin+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.put(url, {withCredentials: true});
    }

}