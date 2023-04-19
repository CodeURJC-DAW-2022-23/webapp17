import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, throwError } from "rxjs";
import { environment } from "../environment";
import { ApiResources } from "../apiresources";

@Injectable({ providedIn: 'root' })
export class CartService {
    constructor(private httpClient: HttpClient){}

    moreQuantity(id: number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.MoreQuantity+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.put(url, { withCredentials: true }).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

    getCart(){
        let url = environment.apiUrl+"/"+ApiResources.Cart;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { withCredentials: true }).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

}