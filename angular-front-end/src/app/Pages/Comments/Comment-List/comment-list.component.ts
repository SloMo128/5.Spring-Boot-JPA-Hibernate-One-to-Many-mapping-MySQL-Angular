import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FeedBack } from 'src/app/Model/feedback';
import { CommnetApiService } from 'src/app/Service/comment.service';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit {
  comments: Comment[] = [];
  feedback = new FeedBack("", "");
  isLoading: boolean = true;
  isLoadingPage: boolean = false;
  postId: string;

  totalCustomers: number = 0;
  pagination: number = 0;
  customerPage: number = 5;
  sortField: string = "username";
  sortOrder: string = "DESC";
  page = this.sortOrder
  chengePageIco: boolean = false

  constructor(
    private commnetService: CommnetApiService,
    private router: Router
  ) { }

  pageDirection(page) {
    this.sortOrder = this.sortOrder === 'ASC' ? 'DESC' : 'ASC';
    this.getListCommnets(this.postId);
  }

  ngOnInit(): void {
    this.postId = localStorage.getItem('postId');
    if (!this.postId) {
      alert("ID non trovato!");
      this.router.navigate(['']);
      return;
    }
    this.getListCommnets(this.postId);
    this.feedback = { feedbackType: '', feedbackmsg: '' };

  }

  getListCommnets(postId: string): void {
    this.comments = [];
    let params = new HttpParams;

    params = params.append('page', "" + this.pagination);
    params = params.append('size', "" + this.customerPage);
    params = params.append('sort', this.sortField);
    params = params.append('order', this.sortOrder)

    this.commnetService.getListCommnet(postId, params).subscribe({
      next: (data: any) => {
        if (data.length !== 0) {
          this.comments = data.comments;
          this.totalCustomers = data.commentRepo;
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

  renderPage(event: number) {
    this.pagination = event - 1;
    this.getListCommnets(this.postId);
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
    this.router.navigate(['/post/']);
  }

}
