import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Post} from "../../model/post";
import {PostService} from "../../services/post.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  @Input() post: Post;
  currentUserExist: boolean = false;
  currentFile: File;


  constructor(private postService: PostService, private router: Router, private modalService: NgbModal) {
  }


  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;
  }

  redirectCommunity(id: number) {
    this.router.navigateByUrl('/community/' + id);

  }

}
