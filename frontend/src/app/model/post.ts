export class Post {
  id: number;
  imagePath: string;
  title: string;
  text: string;
  creationDate: number;
  username: string;
  communityId: number;
  userVote : string;
  carma: number;



  constructor(obj?: any){
    this.id = obj && obj.id || null;
    this.imagePath = obj && obj.imagePath || null;
    this.title = obj && obj.title || null;
    this.text = obj && obj.text || null;
    this.creationDate = obj && obj.creationDate || null;
    this.username = obj && obj.username || null;
    this.communityId = obj && obj.communityId || null;
    this.userVote = obj && obj.userVote || null;
    this.carma = obj && obj.carma || null;
  }
}
