import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../../model/post";
import {PostService} from "../../services/post.service";
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {
  currentUserExist: boolean = false;

  @Input()
  public post: Post;
  thenBlock: any;
  elseBlock: any;
  thenBlock2: any;
  elseBlock2: any;
  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;
  }

  upvotePost(post: Post){
    this.postService.votePost("UPVOTE", post.id)
      .subscribe((data: Post) => {
        this.post= data;
      });
  }
  downvotePost(post: Post){
    this.postService.votePost("DOWNVOTE", post.id)
      .subscribe((data: Post) => {
        this.post= data;
      });
  }

}
