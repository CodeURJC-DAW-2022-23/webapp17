import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError, of } from 'rxjs';

import { environment } from '../environment';
import { ApiResources } from '../apiresources';
import { Page } from '../model/pageable.model';
import { UserProfile } from '../model/user.model';
import { CartPackage } from '../model/cart.model';

@Injectable({ providedIn: 'root' })
export class UserService {

    constructor(private httpClient: HttpClient) {
        
    }

    getTest(to:Observable<any>) : Observable<any>{
        var observable = new Observable<any>((subscriber) => {
                console.log('Test Function');
                this.logout().subscribe(()=>{
                    this.login("admin@admin", "1234").subscribe(()=>{
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
            map(response => response as UserProfile),
            catchError(error => throwError(() => err))
        );
    }

    login(email : string, password : string) : Observable<any>{
        var data = {
            "email": email,
            "password": password
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

    register(email : string, password : string, name : string) : Observable<any>{
        var data = {
            "email": email,
            "password": password,
            "name" : name
        }
        let url = environment.apiUrl+"/"+ApiResources.Register;
        const err = new Error('Server error.');
        return this.httpClient.post(url, data, {withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

    getUsers(page: number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Users;
        const err = new Error('Server error.');
        var data = {};
        if(page != null)
            data = {
                "pageNumber": page
            }
        return this.httpClient.get(url, { params: data, withCredentials: true}).pipe(
            map(response =>response as Page<UserProfile>),
            catchError(error => throwError(() => err))
        );
    }

    getNoPaginatedUsers() : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Users;
        var data = {}
        data = {
            "pageNumber" : -1
        }
        const err = new Error('Server error.');
        return this.httpClient.get(url, { params:data, withCredentials: true}).pipe(
            map(response =>response as Array<UserProfile>),
            catchError(error => throwError(() => err))
        );
    }

    modifyUser(email? : string, password? : string, name? : string, bio? : string, id? : string) : Observable<any>{
        var data = {
            "email": email,
            "newPassword": password,
            "name" : name,
            "newBio" : bio,
        }
        let url
        if(id){
            url = environment.apiUrl+"/"+ApiResources.User+"/";
        }else{
            url = environment.apiUrl+"/"+ApiResources.User;
        }
        const err = new Error('Server error.');
        return this.httpClient.put(url, data, {withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }

    modifyUserImage(id : number, blob : Blob) : Observable<any>{

        let url = environment.apiUrl+"/"+ApiResources.Users+"/"+id+"/image";
        const err = new Error('Server error.');

        const formData = new FormData();

        // Pass the image file name as the third parameter if necessary.
        formData.append('imageFile', blob, "profile.png");

        return this.httpClient.post(url, formData, {withCredentials: true}).pipe(
            map(response =>response),
            catchError(error => throwError(() => err))
        );
    }


    getOrders(page? : number){
        var tpage = page;
        if(tpage == null) tpage = 0;
        var data = {
            "pageNumber" : tpage
        }
        let url = environment.apiUrl+"/"+ApiResources.Orders;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { withCredentials: true, params:data}).pipe(
            map(response =>response as Page<CartPackage>),
            catchError(error => throwError(() => err))
        );
    }

    getOrder(id : number){
        let url = environment.apiUrl+"/"+ApiResources.Order+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.get(url, { withCredentials: true }).pipe(
            map(response =>response as CartPackage),
            catchError(error => throwError(() => err))
        );
    }

    deleteUser(id:string){
        let url = environment.apiUrl+"/"+ApiResources.User+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.delete(url, {withCredentials: true});
    }

    createUser(data:{}){
        let url = environment.apiUrl+"/"+ApiResources.User;
        const err = new Error('Server error.');
        return this.httpClient.post(url, data, {withCredentials: true});
    }

}