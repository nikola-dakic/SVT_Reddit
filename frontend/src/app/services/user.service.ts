import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Community} from "../model/community";


@Injectable({
  providedIn: 'root'
})
export class UserService {


  private readonly path: string = "api/users/registration";
  private readonly pathUpdateUser: string = "api/users/";
  private readonly pathChangePassword: string = "api/users/changePassword/";

  constructor(private http: HttpClient) {
  }

  signUp(email: string, username: string, password: string): Observable<any> {
    return this.http.post<any>(this.path, {email: email, username: username, password: password});
  }

  getMyInfo(): Observable<User> {return this.http.get<User>("api/users")

  }

  updateUser(updatedEmail: string, updatedDisplayName: string, updatedDescription: string, userId: string) {

    var headers = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json"
    });

    return this.http.put(this.pathUpdateUser + userId, {
      email: updatedEmail,
      description: updatedDescription,
      displayName: updatedDisplayName

    }, {headers: headers});

  }

  changePassword(currentPassword: string, newPassword: string, confirmNewPassword: string) {
    var headers = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json"
    });


    return this.http.patch(this.pathChangePassword, {
      oldPassword: currentPassword,
      newPassword: newPassword,
      newRepeatedPassword: confirmNewPassword
    }, {headers: headers});


  }


}
