import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../environment';
import { ApiResources } from '../apiresources';
import { Page } from '../model/pageable.model';
import { Comment } from '../model/comment.model';

@Injectable({ providedIn: 'root' })
export class CommentService {

    constructor(private httpClient: HttpClient) {}
    
    getComments(page?:number) : Observable<any>{
        let url = environment.apiUrl+"/"+ApiResources.Comments;
        const err = new Error('Server error.');
        var data = {};
        if(page != null)
            data = {
                "page": page
            }
        return this.httpClient.get(url, { params:data, withCredentials: true}).pipe(
            map(response => response as Page<Comment>),
            catchError(error => throwError(() => err))
        );
    }

    deleteComment(id:string){
        let url = environment.apiUrl+"/"+ApiResources.Comment+"/"+id;
        const err = new Error('Server error.');
        return this.httpClient.delete(url, {withCredentials: true});
    }
}