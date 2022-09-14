import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  emailValue: string= "";
  usernameValue: string= "";
  passwordValue: string= "";

  constructor(private userService: UserService,private router: Router) { }

  ngOnInit(): void {
  }
  signUp(){
    this.userService.signUp(this.emailValue, this.usernameValue, this.passwordValue)
      .subscribe()
    this.router.navigateByUrl('');


  }

}
