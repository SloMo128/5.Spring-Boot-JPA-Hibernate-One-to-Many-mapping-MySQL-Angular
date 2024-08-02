import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

    constructor(
        private postService: PostApiService,
        private router: Router
    ) { }


    ngOnInit(): void {
        this.postId = localStorage.getItem('postId');
        if (!this.postId) {
            alert("ID non trovato!");
            this.router.navigate(['']);
            return;
        }
        this.getPost(this.postId);
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
}
