import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  addForm: FormGroup;
  editForm: FormGroup;
  visibility = false;
  
  totalCustomers: number = 0;
  pagination: number = 0;
  customerPage: number = 5;
  sortField: string = "title";
  sortOrder: string = "DESC";
  page = this. sortOrder
  chengePageIco: boolean = false

  constructor(
      private postService: PostApiService,
      private router: Router,
      private fb: FormBuilder,
  ) { }
  
  pageDirection(page){
    this.sortOrder = this.sortOrder === 'ASC' ? 'DESC' : 'ASC';
    this.getList();
  }

  toggleVisibility() {
    this.visibility = !this.visibility;
  }

  ngOnInit(): void {
      this.getList();
      this.feedback = { feedbackType: '', feedbackmsg: '' };

      this.addForm = this.fb.group({
        title: ['', [Validators.required]],
        description: ['', [Validators.required]],
        content: ['', [Validators.required]],
        createdAt: [''],
      });

      localStorage.removeItem('postId');
  }

  getList(): void {
      this.posts = [];
      let params = new HttpParams;

      params = params.append('page', "" + this.pagination);
      params = params.append('size', "" + this.customerPage);
      params = params.append('sort', this.sortField);
      params = params.append('order', this.sortOrder)

      this.postService.getListPost(params).subscribe({
          next: (data: any) => {
              if (data.length !== 0) {
                  this.posts = data.posts;
                  this.totalCustomers = data.postsCount;
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
    this.getList();
  }

  addPost() {
    this.postService.add(this.addForm.value).subscribe({
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

  get title() {
    return this.addForm.get('title');
  }

  get description() {
    return this.addForm.get('description');
  }

  get content() {
    return this.addForm.get('content');
  }

  get createdAt() {
    return this.addForm.get('createdAt');
  }
}
