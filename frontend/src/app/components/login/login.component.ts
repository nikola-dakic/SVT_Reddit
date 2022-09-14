import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usernameValue: any;
  passwordValue: any;
  isBadCredentials: boolean= false;
  constructor(private authService: AuthService,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  login(event: any) {
    this.isBadCredentials = false;
    event.preventDefault;
    this.authService.login(this.usernameValue, this.passwordValue)
      .subscribe(() => {
        this.userService.getMyInfo().subscribe();
        this.router.navigateByUrl('');
      },() => {
        this.isBadCredentials = true
      });
  }
}
