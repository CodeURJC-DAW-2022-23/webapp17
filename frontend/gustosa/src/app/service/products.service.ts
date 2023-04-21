import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError, of } from 'rxjs';
import { environment } from '../environment';
import { ApiResources } from '../apiresources';
import { Page } from '../model/pageable.model';
import { Product, ProductsPackage } from '../model/product.model';

@Injectable({ providedIn: 'root' })
export class ProductsService {

    constructor(private httpClient: HttpClient) {}

    getProducts(page:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Products+'?page=' + page;
        const err = new Error('Server error.');;
        return this.httpClient.get(url, { withCredentials: true }).pipe(
            map(response =>response as ProductsPackage),
            catchError(error => throwError(() => err))
        );
    }

    getIndividualProduct(id:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Products+'/'+id;
        const err = new Error('Server error.');;
        return this.httpClient.get(url, { withCredentials: true }).pipe(
            map(response =>response as Product),
            catchError(error => throwError(() => err))
        );
    }

}