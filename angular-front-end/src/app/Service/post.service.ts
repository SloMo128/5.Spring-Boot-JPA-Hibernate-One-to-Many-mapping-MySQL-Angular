import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Post } from '../Model/post.model';

@Injectable()
export class PostApiService {

    baseURL: string = "http://localhost:8080/spring-rest-api/";

    constructor(private http: HttpClient){}

    getListPost(params: HttpParams): Observable<Post[]> {
        return this.http.get<Post[]>(this.baseURL + 'posts',  { params })
    }

    getPost(id: string): Observable<Post> {
        return this.http.get<Post>(this.baseURL + 'post/' + id)
    }

    editPost(postId: string, bodyPost: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(bodyPost);
        return this.http.put<Post>(this.baseURL + "post/put/" + postId,body, { 'headers': headers } )
    }

    add(emp: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(emp);
        console.log(body)
        return this.http.post<Post>(this.baseURL + 'post', body, { 'headers': headers })
        //.pipe(catchError((err) => this.handleError('POST', err)));
    }

    deletePost(id: string): Observable<void> {
        return this.http.delete<void>(this.baseURL + "post/delete/" + id);
      }

}