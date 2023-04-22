import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../environment';
import { ApiResources } from '../apiresources';
import { Statistics } from '../model/statistics';

@Injectable({ providedIn: 'root' })
export class DashboardService {

    constructor(private httpClient: HttpClient) {}
    
    getStatistics() : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Statistics;
        const err = new Error('Server error.');
        return this.httpClient.get(url, {withCredentials: true}).pipe(
            map(response => response as Statistics),
            catchError(error => throwError(() => err))
        );
    }
}