import {Component, OnInit} from '@angular/core';
import {Community} from "../../model/community";
import {CommunityService} from "../../services/community.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {PostService} from "../../services/post.service";
import {UserService} from "../../services/user.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.css']
})
export class CommunityComponent implements OnInit {
  community: Community;
  id: number;
  isComLoaded: boolean = false;
  isUserLoaded: boolean = false;
  updatedName: string = "";
  updatedDescription: string = "";
  suspendReason: string = "";
  currentUserExist: boolean = false;
  current: User;
  userBanned:boolean = false;

  constructor(private postService: PostService, private userService: UserService, private communityService: CommunityService, private route: ActivatedRoute, private router: Router, config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit(): void {
    this.userBanned = false;
    this.currentUserExist = localStorage.getItem("jwt") !== null;
    this.userService.getMyInfo().subscribe(
      (currentUser) => {
        this.current = currentUser;
        this.isUserLoaded = true;
      }, () => {
        this.isUserLoaded = true;
      }
    );

    this.route
      .paramMap
      .subscribe(params => {
        this.id = Number(params.get("id"))
      });
    this.communityService.getCommunity(this.id)
      .subscribe((data: Community) => {
        this.community = data;

        this.isComLoaded = true;
      });

  }

  open(content: any) {
    this.updatedName = this.community.name;
    this.updatedDescription = this.community.description;
    this.modalService.open(content);
  }

  newPost() {
    this.router.navigateByUrl('/createPost/' + this.id);
  }

  editCommunity() {
    this.community.name = this.updatedName;
    this.community.description = this.updatedDescription;
    this.communityService.updateCommunity(this.community)
      .subscribe(() => this.router.navigateByUrl("/community/" + this.community.id));
  }

  access() {
    if (!this.current)
    {
      return false;
    }
    if (this.current.role == "ROLE_ADMIN") {
      return true;
    }
    return this.community.moderatorUsernames.includes(this.current.username);

  }


  accessAdmin() {
    if (!this.current)
    {
      return false;
    }
    return this.current.role == "ROLE_ADMIN";

  }
}
