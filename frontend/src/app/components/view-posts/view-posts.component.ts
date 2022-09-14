import {Component, OnInit} from '@angular/core';
import {Post} from "../../model/post";
import {PostService} from "../../services/post.service";
import {Router} from "@angular/router";
import {Community} from "../../model/community";
import {CommunityService} from "../../services/community.service";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-view-posts',
  templateUrl: './view-posts.component.html',
  styleUrls: ['./view-posts.component.css']
})
export class ViewPostsComponent implements OnInit {
  allPosts: Post[] = [];
  allCom: Community[] = [];
  currentUser: boolean = false;
  current: User;
  isUserLoaded:boolean= false

  constructor(private userService: UserService, private authService : AuthService, private communityService: CommunityService, private postService: PostService, private router: Router) {
  }

  ngOnInit(): void {
    this.currentUser = localStorage.getItem("jwt") !== null;

    this.authService.loginFunction.subscribe((name: string) => {
      this.currentUser = localStorage.getItem("jwt") !== null;
    });


    this.communityService.getAll()
      .subscribe((data: Community[]) => {
        console.log("svi com")
        this.allCom = data;
        console.log(data)
      });

    this.postService.getAll()
      .subscribe((data: Post[]) => {
        this.allPosts = data;
      });
    this.userService.getMyInfo().subscribe(
      (currentUser) => {
        this.current = currentUser;
        this.isUserLoaded = true;
      }, () => {
        this.isUserLoaded = true;
      }
    );


  }

  newCommunity() {
    this.router.navigateByUrl('/createCommunity');
  }


  myProfile() {
    this.router.navigateByUrl('/myProfile');
  }

}
