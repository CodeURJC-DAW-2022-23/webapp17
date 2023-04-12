import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
    getTest() : Observable<any>{
        var observable = new Observable<any>((subscriber) => {
                console.log('Test Function');
                setTimeout(() => {
                    console.log("Executed service");
                    subscriber.next({
                        "name" : "Sagar Mahajan"
                    });
                    subscriber.complete();
                },5000);
                
            });
        return observable;
    }
}