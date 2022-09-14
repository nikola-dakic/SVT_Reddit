export class User{
  id: number;
  username: string;
  email: string;
  avatar: string;
  description: string;
  registrationDate: string;
  displayName: string;
  userCarma: string;
  role: string;
  banned: boolean;


  constructor(obj?: any){
    this.id = obj && obj.id || null;
    this.username = obj && obj.username || null;
    this.email = obj && obj.email || null;
    this.avatar = obj && obj.avatar || null;
    this.description = obj && obj.description || null;
    this.registrationDate = obj && obj.registrationDate || null;
    this.displayName = obj && obj.displayName || null;
    this.userCarma = obj && obj.userCarma || null;
    this.role = obj && obj.role || null;
    this.banned = obj && obj.banned || null;
  }
}
