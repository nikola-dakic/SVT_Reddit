import {Component, OnInit} from '@angular/core';
import {CommunityService} from "../../services/community.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-community',
  templateUrl: './create-community.component.html',
  styleUrls: ['./create-community.component.css']
})
export class CreateCommunityComponent implements OnInit {
  name: string = "";
  description: string = "";
  currentUserExist: boolean = false;

  constructor(private communityService: CommunityService, private router: Router) {
  }

  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;
  }

  createCommunity() {
    this.communityService.createCommunity(this.name, this.description)
      .subscribe()
    this.router.navigateByUrl('');

  }

  cancel() {
    this.router.navigateByUrl('');
  }
}
