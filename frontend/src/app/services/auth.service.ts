import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserService} from './user.service';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable()
export class AuthService {

  constructor(
    private userService: UserService,
    private router: Router,
    private http: HttpClient
  ) {
  }

  loginFunction = new EventEmitter();

  private access_token : any;
  private readonly path: string = "api/users/login";

  login(username: string, password: string) {
    const body = {
      'username': username,
      'password': password
    };
    var headers = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json"
    });
    return this.http.post(this.path, JSON.stringify(body), {headers: headers})
      .pipe(map((res: any) => {
        console.log('Login success');
        this.access_token = res.accessToken;
        localStorage.setItem("jwt", res.accessToken)
        this.loginFunction.emit();
      }));
  }

  logout() {
    localStorage.removeItem("jwt");
    this.access_token = null;
    this.router.navigate(['/login']);
  }

  getToken() {
    this.access_token = localStorage.getItem("jwt");
    return this.access_token;
  }

}
