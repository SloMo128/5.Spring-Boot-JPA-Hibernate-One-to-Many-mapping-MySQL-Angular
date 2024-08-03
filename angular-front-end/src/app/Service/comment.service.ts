import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Post } from '../Model/post.model';

@Injectable()
export class CommnetApiService {

    baseURL: string = "http://localhost:8080/spring-rest-api/";

    constructor(private http: HttpClient){}

    getListCommnet(postId: string, params: HttpParams): Observable<Post[]> {
        return this.http.get<Post[]>(this.baseURL + 'posts/comments/' + postId,  { params })
    }

    getCommnet(commnetId: string): Observable<Post> {
        return this.http.get<Post>(this.baseURL + 'commnet/' + commnetId)
    }

    /*update(id: string, emp: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(emp);
        return this.http.put<Post>(this.baseURL + 'post/put/' + id, body, { 'headers': headers })
    }*/

    add(postId: string, comment: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(comment);
        console.log(body)
        return this.http.post<Post>(this.baseURL + 'posts/commentspost/' + postId, body, { 'headers': headers })
        //.pipe(catchError((err) => this.handleError('POST', err)));
    }

    deletePost(postId: string, commentId: string): Observable<Post> {
        return this.http.delete<Post>(this.baseURL + `/postsdelite/${postId}/commentsdelite/${commentId}`)
    }

}