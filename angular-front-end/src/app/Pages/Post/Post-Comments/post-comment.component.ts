import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FeedBack } from 'src/app/Model/feedback';
import { Post } from 'src/app/Model/post.model';
import { PostApiService } from 'src/app/Service/post.service';

@Component({
    selector: 'app-comment',
    templateUrl: './post-comment.component.html',
})
export class PostCommentComponent implements OnInit {
    //posts: Post[] = [];
    post: Post = null;
    feedback = new FeedBack("", "");
    isLoading: boolean = true;
    isLoadingPage: boolean = false;
    postId: string;
    editForm: FormGroup;
    editingPostId: string = null

    constructor(
        private postService: PostApiService,
        private router: Router,
        private fb: FormBuilder,
    ) { }


    ngOnInit(): void {
        this.postId = localStorage.getItem('postId');
        if (!this.postId) {
            alert("ID non trovato!");
            this.router.navigate(['']);
            return;
        }
        this.getPost(this.postId);

        this.editForm = this.fb.group({
            content: ['', [Validators.required]],
          });

        this.feedback = { feedbackType: '', feedbackmsg: '' };

        //localStorage.removeItem('postId');
    }

    getPost(id: string): void {
        this.post = null

        this.postService.getPost(id).subscribe({
            next: (data: any) => {
                if (data.length !== 0) {
                    this.post = data;
                    //console.log(JSON.stringify(this.post));
                    this.isLoading = false;
                }
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

    editPost(postId: string) {
        this.postService.editPost(postId, this.editForm.value).subscribe({
          next: (data) => {
            this.feedback = { feedbackType: 'success', feedbackmsg: 'Post updated successfully' };
            this.editingPostId = null;
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
    
      setEditingPost(post: any) {
        this.editingPostId = post.id;
        this.editForm.patchValue({ content: post.content });
      }
    
      cancelEdit() {
        this.editingPostId = null;
      }
}
