import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { FeedBack } from 'src/app/Model/feedback';
import { Post } from 'src/app/Model/post.model';
import { PostApiService } from 'src/app/Service/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
})
export class PostListComponent implements OnInit {

  posts: Post[] = [];
  feedback = new FeedBack("", "");
  isLoading: boolean = true;
  isLoadingPage: boolean = false;
  //designations: postProfile[] = [];

  constructor(
      private postService: PostApiService,
      //private postProfileSerice: postProfileService,
      private router: Router
  ) { }

  ngOnInit(): void {
      this.getList();

      this.feedback = { feedbackType: '', feedbackmsg: '' };

      localStorage.removeItem('postId');
  }

  getList(): void {
      this.posts = [];
      this.postService.getListPost().subscribe({
          next: (data: any) => {
              if (data.length !== 0) {
                  this.posts = data.posts;
                  console.log(data)
                  this.isLoadingPage = true;
                  this.isLoading = false;
              };
          },
          error: (err: any) => {
              console.log(err);
              this.isLoading = false;
              this.feedback = {
                  feedbackType: err.feedbackType,
                  feedbackmsg: err.feedbackmsg,
              };
              console.log(JSON.stringify(this.feedback));
              throw new Error();
          },
          complete: () => {
              this.feedback = { feedbackType: 'success', feedbackmsg: 'loaded' };
          },
      });
  }

  /*deletepost(id: string, index) {
      if (window.confirm("Are you sure you want to delete this post?")) {
          this.postService.deletepost(id).subscribe({
              next: (data) => {
                  this.post.splice(index, 1);
              },
              error: (err: any) => {
                  console.log(err);
                  this.feedback = {
                      feedbackType: err.feedbackType,
                      feedbackmsg: err.feedbackmsg,
                  };
                  throw new Error();
              }
          });
      }
  }*/

  saveDataAndNavigate(id: string) {
      localStorage.setItem('postId', id);
      this.router.navigate(['/editpost/']);
  }

}
