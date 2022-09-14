import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Post} from "../model/post";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private readonly path: string = "api/posts";
  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Post[]> {
    return this.http.get<Post[]>(this.path);
  }

  getPost(postId: number):Observable<Post> {
    return this.http.get<Post>(this.path + "/" + postId);
  }

  createPost(title: string, text: string, communityId: number): Observable<Post> {
    return this.http.post<Post>(this.path, {
      title: title,
      text: text,
      communityId: communityId
    });
  }

  updatePost(updatedPost: Post): Observable<any> {

    var headers = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json"
    });

    return this.http.patch(this.path + "/" + updatedPost.id, {
      title: updatedPost.title,
      text: updatedPost.text
    }, {headers: headers});

  }



  votePost(vote: string, idPost: number): Observable<Post> {
    var headers = new HttpHeaders({
      "Content-Type": "application/json",
      "Accept": "application/json"
    });
    return this.http.patch<Post>(this.path + "/" + idPost + "/vote", {
      voteType: vote
    }, {headers: headers});

  }

  deletePost(postId: number) {
    return this.http.delete(this.path + "/" + postId)
  }
}
