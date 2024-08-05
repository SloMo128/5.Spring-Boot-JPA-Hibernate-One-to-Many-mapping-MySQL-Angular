import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Post } from '../Model/post.model';

@Injectable()
export class CommentApiService {

    baseURL: string = "http://localhost:8080/spring-rest-api/";

    constructor(private http: HttpClient){}

    getListComment(postId: string, params: HttpParams): Observable<Post[]> {
        return this.http.get<Post[]>(this.baseURL + 'posts/comments/' + postId,  { params })
    }

    getComment(commentId: string): Observable<Post> {
        return this.http.get<Post>(this.baseURL + 'comment/' + commentId)
    }

    editComment(postId: string, commentId:string, bodyComm: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(bodyComm);
        return this.http.put<Post>(this.baseURL + `posts/${postId}/comments/${commentId}`,body, { 'headers': headers } )
    }

    addComment(postId: string, comment: Post): Observable<Post> {
        const headers = { 'content-type': 'application/json' }
        const body = JSON.stringify(comment);
        console.log(body)
        return this.http.post<Post>(this.baseURL + 'posts/commentspost/' + postId, body, { 'headers': headers })
        //.pipe(catchError((err) => this.handleError('POST', err)));
    }

    deletecomment(postId: string, commentId: string): Observable<Post> {
        return this.http.delete<Post>(this.baseURL + `postsdelite/${postId}/commentsdelite/${commentId}`)
    }

}