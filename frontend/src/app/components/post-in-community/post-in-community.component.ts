import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Post} from "../../model/post";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {PostService} from "../../services/post.service";
import {Router} from "@angular/router";
import {User} from "../../model/user";
import {Community} from "../../model/community";
import {UserService} from "../../services/user.service";
import {CommunityService} from "../../services/community.service";

@Component({
  selector: 'app-post-in-community',
  templateUrl: './post-in-community.component.html',
  styleUrls: ['./post-in-community.component.css']
})
export class PostInCommunityComponent implements OnInit {
  @Input() post: Post;
  @Input() community: Community;
  @Input() currentUser: User;
  @Output() postDeleted: EventEmitter<any> = new EventEmitter<any>();

  updatedTitle: string = "";
  currentUserExist: boolean = false;
  updatedText: string = "";
  retrievedImage: any;


  constructor(private postService: PostService, private userService: UserService, private communityService: CommunityService,
              private router: Router, config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;

  }

  open(content: any) {
    this.updatedTitle = this.post.title;
    this.updatedText = this.post.text;
    this.modalService.open(content);
  }

  editPost() {
    this.post.title = this.updatedTitle;
    this.post.text = this.updatedText;
    this.postService.updatePost(this.post)
      .subscribe(() => {
        this.router.navigateByUrl('');
      });
  }




  canEdit(post: Post) {
    if (!this.currentUser)
    {
      return false;
    }
    if (this.currentUser.role == "ROLE_ADMIN") {
      return true;
    }
    if (this.community.moderatorUsernames.includes(this.currentUser.username)) {
      return true;
    }

    return post.username == this.currentUser.username;

  }

  redirectCommentsPost(postId: number) {
    this.router.navigateByUrl('/details/' + postId);
  }

  deletePost(post: Post) {
    this.postService.deletePost(post.id)
      .subscribe(() => this.postDeleted.emit()
      )
  }
}
