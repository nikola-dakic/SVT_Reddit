import {Component, OnInit} from '@angular/core';
import {PostService} from "../../services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import {Community} from "../../model/community";
import {CommunityService} from "../../services/community.service";
import {Post} from "../../model/post";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  title: string = "";
  text: string = "";
  id: number;
  community: Community;
  currentUserExist: boolean = false;


  constructor(private  communityService: CommunityService, private sanitizer: DomSanitizer, private postService: PostService, private route: ActivatedRoute, private router: Router) {
  }



  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;
    this.route
      .paramMap
      .subscribe(params => {
        this.id = Number(params.get("id"))
        this.communityService.getCommunity(this.id)
          .subscribe((data: Community) => {
            this.community = data;
          });
      });
  }

  createPost() {
    this.postService.createPost(this.title, this.text, this.id)
      .subscribe((post: Post) => {
        this.router.navigateByUrl('');
      });

  }

  cancel() {
    this.router.navigateByUrl('community/' + this.id);
  }

}
