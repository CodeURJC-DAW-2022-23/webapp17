import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';

import { environment } from '../environment';
import { ApiResources } from '../apiresources';

@Injectable({ providedIn: 'root' })
export class UserService {

    constructor(private httpClient: HttpClient) {}

    getTest() : Observable<any>{
        var observable = new Observable<any>((subscriber) => {
                console.log('Test Function');
                this.login().subscribe(()=>{
                    this.getUser().subscribe(response => { 
                        subscriber.next(response);
                        subscriber.complete();
                    });
                });
            });
        return observable;
    }
    
    getUser() : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.User;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { params:{"email":"test@test.com"}, withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

    login() : Observable<any>{
        var data = {
            "email": "admin@admin",
            "password": "1234"
        }
        let url = environment.apiUrl+"/"+ApiResources.Login;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { withCredentials: true, params:data}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }
}