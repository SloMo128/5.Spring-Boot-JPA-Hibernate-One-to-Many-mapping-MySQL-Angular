import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxPaginationModule } from 'ngx-pagination';
import { ErrorCodeService } from './Service/http.error.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { GlobalHttpInterceptorService } from './Service/global-http-Interceptor.service';

import { PageNotFoundComponent } from './Pages/Comments/Page-not-found/page.not.found.component';
import { PostListComponent } from './Pages/Post/Post-List/post-list.component';
import { PostEditComponent } from './Pages/Post/Post-Edit/post-edit.component';
import { PostUpdateComponent } from './Pages/Post/Post-Update/post-update.component';
import { CommentListComponent } from './Pages/Comments/comment-list/comment-list.component';
import { CommentEditComponent } from './Pages/Comments/comment-edit/comment-edit.component';
import { CommentUpdateComponent } from './Pages/Comments/comment-update/comment-update.component';



@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    PostListComponent,
    PostEditComponent,
    PostUpdateComponent,
    CommentListComponent,
    CommentEditComponent,
    CommentUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPaginationModule,

  ],
  providers: [
    ErrorCodeService,
    { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
