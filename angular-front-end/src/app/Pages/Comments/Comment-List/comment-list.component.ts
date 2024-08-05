import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FeedBack } from 'src/app/Model/feedback';
import { CommentApiService } from 'src/app/Service/comment.service';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit {
  comments: Comment[] = [];
  feedback = new FeedBack("", "");
  isLoading: boolean = true;
  isLoadingPage: boolean = false;
  isLoadingPageAdd: boolean = false;
  postId: string;
  edit: string;
  addForm: FormGroup;
  editForm: FormGroup;
  editingCommentId: string;

  totalCustomers: number = 0;
  pagination: number = 0;
  customerPage: number = 5;
  sortField: string = "createdAt";
  sortOrder: string = "DESC";

  constructor(
    private commentService: CommentApiService,
    private router: Router,
    private fb: FormBuilder,
  ) { }

  pageDirection(page) {
    this.sortOrder = this.sortOrder === 'ASC' ? 'DESC' : 'ASC';
    this.getListCommnet(this.postId);
  }

  ngOnInit(): void {
    this.postId = localStorage.getItem('postId');
    if (!this.postId) {
      alert("ID non trovato!");
      this.router.navigate(['']);
      return;
    }

    this.addForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(25)]],
      body: ['', [Validators.required]],
    });

    this.editForm = this.fb.group({
      body: ['', [Validators.required]],
    });

    this.getListCommnet(this.postId);
    this.feedback = { feedbackType: '', feedbackmsg: '' };
  }

  getListCommnet(postId: string): void {
    this.comments = [];
    let params = new HttpParams();
    console.log(postId);
    params = params.append('page', "" + this.pagination);
    params = params.append('size', "" + this.customerPage);
    params = params.append('sort', this.sortField);
    params = params.append('order', this.sortOrder);

    this.commentService.getListComment(postId, params).subscribe({
      next: (data: any) => {
        if (data.comments.length !== 0) {
          this.comments = data.comments;
          this.totalCustomers = data.totalComments; // Ensure your backend returns this value
        }
        this.isLoadingPage = true;
        this.isLoading = false;
        this.isLoadingPageAdd = true;
      },
      error: (err: any) => {
        //console.log(err.feedbackmsg);
        if (err.feedbackmsg !== '404: Nessun dato trovato') {
          this.feedback = {
            feedbackType: err.feedbackType,
            feedbackmsg: err.feedbackmsg,
          };
          this.isLoadingPageAdd = true;
        } else {
          this.isLoadingPageAdd = true;
        }

        throw new Error();
      },
      complete: () => {
        this.feedback = { feedbackType: 'success', feedbackmsg: 'loaded' };
      },
    });
  }

  renderPage(event: number) {
    this.pagination = event - 1;
    this.getListCommnet(this.postId);
  }

  addCommnet(postId: string) {
    this.commentService.addComment(postId, this.addForm.value).subscribe({
      next: (data) => {
        console.log(this.addForm.value);
        this.feedback = { feedbackType: 'success', feedbackmsg: 'Comment added successfully' };
        location.reload();
      },
      error: (err: any) => {
        console.log(err);
        this.isLoadingPage = true;
        this.isLoading = false;
        this.feedback = {
          feedbackType: err.feedbackType,
          feedbackmsg: err.feedbackmsg,
        };
        throw new Error();
      },
      complete: () => {
        this.isLoadingPage = true;
        this.isLoading = false;
      },
    });
  }

  editCommnet(postId: string, commentId: string) {
    this.commentService.editComment(postId, commentId, this.editForm.value).subscribe({
      next: (data) => {
        this.feedback = { feedbackType: 'success', feedbackmsg: 'Comment updated successfully' };
        this.editingCommentId = null;
        this.getListCommnet(postId);
      },
      error: (err: any) => {
        console.log(err);
        this.isLoadingPage = true;
        this.isLoading = false;
        this.feedback = {
          feedbackType: err.feedbackType,
          feedbackmsg: err.feedbackmsg,
        };
        throw new Error();
      },
      complete: () => {
        this.isLoadingPage = true;
        this.isLoading = false;
      },
    });
  }

  setEditingComment(comment: any) {
    this.editingCommentId = comment.id;
    this.editForm.patchValue({ body: comment.body });
    this.editForm.patchValue({ username: comment.username });
  }

  cancelEdit() {
    this.editingCommentId = null;
  }

  deleteCommnet(postId: string, commnetId: string, index) {
    if (window.confirm("Are you sure you want to delete this post?")) {
      this.commentService.deletecomment(postId, commnetId).subscribe({
        next: (data) => {
          this.comments.splice(index, 1);
          this.getListCommnet(postId);
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
  }

  get username() {
    return this.addForm.get('username');
  }

  get body() {
    return this.addForm.get('body');
  }
}
