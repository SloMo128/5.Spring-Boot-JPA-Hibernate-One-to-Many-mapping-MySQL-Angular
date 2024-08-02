import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PageNotFoundComponent } from '../Pages/Comments/Page-not-found/page.not.found.component';

import { PostListComponent } from '../Pages/Post/Post-List/post-list.component';
import { PostCommentComponent } from '../Pages/Post/Post-Comments/post-comment.component';

const routes: Routes = [
  { path: 'posts', component: PostListComponent },
  { path: 'post', component: PostCommentComponent },
  /*{ path: 'add', component: AddComponent},
  { path: 'edit', component: EditComponent },*/
  { path: '', redirectTo: 'posts', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRouting { }
