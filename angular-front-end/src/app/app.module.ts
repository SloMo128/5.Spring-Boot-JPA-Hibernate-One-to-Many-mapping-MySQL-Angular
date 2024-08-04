import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxPaginationModule } from 'ngx-pagination';
import { ErrorCodeService } from './Service/http.error.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { GlobalHttpInterceptorService } from './Service/global-http-Interceptor.service';

import { PageNotFoundComponent } from './Pages/Comments/Page-not-found/page.not.found.component';

import { PostListComponent } from './Pages/Post/Post-List/post-list.component';
import { PostEditComponent } from './Pages/Post/Post-Edit/post-edit.component';
//import { PostAddComponent } from './Pages/Post/Post-Add/post-add.component';
import { PostCommentComponent } from './Pages/Post/Post-Comments/post-comment.component';

import { CommentListComponent } from './Pages/Comments/Comment-List/comment-list.component';
import { CommentEditComponent } from './Pages/Comments/Comment-Edit/comment-edit.component';

import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './Header/header.component';
import { AppRouting } from './Routing/routing';
import { PostApiService } from './Service/post.service';
import { CommnetApiService } from './Service/comment.service';




@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    PostListComponent,
    PostEditComponent,
    //PostAddComponent,
    CommentListComponent,
    CommentEditComponent,
    HeaderComponent,
    PostCommentComponent,
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPaginationModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRouting

  ],
  providers: [
    PostApiService,
    CommnetApiService,
    ErrorCodeService,
    { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
