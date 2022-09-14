import {Post} from "./post";

export class Community {
  id: number;
  name: string;
  description: string;
  creationDate: number;

  moderatorUsernames: string[];
  posts: Post[];



  constructor(obj?: any) {
    this.id = obj && obj.id || null;
    this.name = obj && obj.name || null;
    this.description = obj && obj.description || null;
    this.creationDate = obj && obj.creationDate || null;
    this.moderatorUsernames = obj && obj.moderatorUsernames || null;
    this.posts = obj && obj.posts || null;

  }
}
