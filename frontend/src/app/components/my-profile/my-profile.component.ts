import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  usernameInput: string;
  avatarInput: string;
  emailInput: string;
  carmaInput: string;
  descriptionInput: string;
  displayNameInput: string;
  dateRegistrationInput: any;
  currentUserExist: boolean = false;
  currentUser: any;
  updatedEmail: string = "";
  updatedDescription: string = "";
  updatedDisplayName: string = "";
  currentPassword: string = "";
  newPassword: string = "";
  confirmNewPassword: string = "";


  constructor(private userService: UserService, private authService: AuthService, private router: Router,
               config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  ngOnInit(): void {
    this.currentUserExist = localStorage.getItem("jwt") !== null;
    this.userService.getMyInfo().subscribe((currentUser => {
      this.currentUser = currentUser;
      this.usernameInput = currentUser.username;
      this.avatarInput = currentUser.avatar;
      this.emailInput = currentUser.email;
      this.descriptionInput = currentUser.description;
      this.displayNameInput = currentUser.displayName;
      this.dateRegistrationInput = currentUser.registrationDate;
      this.carmaInput = currentUser.userCarma;

    }));

  }

  openChangeUserInfo(changeInfo: any) {
    this.modalService.open(changeInfo);
    this.updatedDescription = this.descriptionInput;
    this.updatedEmail = this.emailInput;
    this.updatedDisplayName = this.displayNameInput;

  }

  editUser() {
    this.descriptionInput= this.updatedDescription;
    this.emailInput= this.updatedEmail;
    this.displayNameInput= this.updatedDisplayName;

    this.userService.updateUser(this.updatedEmail, this.updatedDisplayName, this.updatedDescription, this.currentUser.id)
      .subscribe(() => {

      });
  }


  openChangePassword(changePassword: any) {
    this.modalService.open(changePassword);
  }

  editPassword() {
    this.userService.changePassword(this.currentPassword, this.newPassword, this.confirmNewPassword)
      .subscribe()
  }


}
