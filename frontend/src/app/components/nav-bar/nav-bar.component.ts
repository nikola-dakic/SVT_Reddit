import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  currentUser: boolean = false;

  constructor(private router: Router, private authService: AuthService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.currentUser = localStorage.getItem("jwt") !== null;

      this.authService.loginFunction.subscribe((name: string) => {
        this.currentUser = localStorage.getItem("jwt") !== null;
      });

  }

  login() {
    this.router.navigateByUrl('/login');
  }

  signUp() {
    this.router.navigateByUrl('/signUp');
  }

  logout() {
    this.currentUser = false;
    this.authService.logout();
  }
}
