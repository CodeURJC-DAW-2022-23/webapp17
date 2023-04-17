import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError, of } from 'rxjs';

import { environment } from '../environment';
import { ApiResources } from '../apiresources';

@Injectable({ providedIn: 'root' })
export class UserService {

    constructor(private httpClient: HttpClient) {}

    getTest(to:Observable<any>) : Observable<any>{
        var observable = new Observable<any>((subscriber) => {
                console.log('Test Function');
                this.logout().subscribe(()=>{
                    this.login().subscribe(()=>{
                        to.subscribe(response => { 
                            subscriber.next(response);
                            subscriber.complete();
                        });
                    });
                });
            });
        return observable;
    }
    
    // Resturns true if there is an user logged in and false if not
    isUserLoggedIn() : Observable<any>{
        return this.getUser().pipe(map(response => true), catchError(error => of(false)));
    }

    getUser(email?:string) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.User;
        const err = new Error('Server error. Cant get user info.');
        var data = {};
        if(email != null)
            data = {
                "email": email
            }
        return this.httpClient.get(url, { params:data, withCredentials: true}).pipe(
            map(response => response),
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

    logout() : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Logout;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }
}