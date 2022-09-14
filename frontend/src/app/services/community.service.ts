import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {Community} from "../model/community";


@Injectable({
  providedIn: 'root'
})
export class CommunityService {
  private readonly path: string = "api/communities";

  constructor(private http: HttpClient) {
  }

  getCommunity(id: number): Observable<Community> {


    return this.http.get<Community>(this.path + "/" + id);

  }

  createCommunity(name: string, description: string): Observable<any> {
    return this.http.post<any>(this.path, {name: name, description: description});
  }

  updateCommunity(updatedCommunity: Community): Observable<any> {
    return this.http.patch(this.path + "/" + updatedCommunity.id, updatedCommunity)

  }

  getAll(): Observable<Community[]> {
    return this.http.get <Community[]>(this.path);
  }

}
