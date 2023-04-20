import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../environment';
import { ApiResources } from '../apiresources';
import { Coupon } from '../model/coupon.model';
import { Page } from '../model/pageable.model';

@Injectable({ providedIn: 'root' })
export class CouponService {

    constructor(private httpClient: HttpClient) {}
    
    getCoupons(page?:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Coupons;
        const err = new Error('Server error.');
        var data = {};
        if(page != null)
            data = {
                "page": page
            }
        return this.httpClient.get(url, { params:data, withCredentials: true}).pipe(
            map(response => response as Page<Coupon>),
            catchError(error => throwError(() => err))
        );
    }

    deleteCoupon(id:string){
        let url = environment.apiUrl+"/"+ApiResources.Coupon+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.delete(url, {withCredentials: true});
    }

}