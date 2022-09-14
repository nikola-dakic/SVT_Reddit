import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {CommunityComponent} from "./components/community/community.component";
import {ViewPostsComponent} from "./components/view-posts/view-posts.component";
import {CreateCommunityComponent} from "./components/create-community/create-community.component";
import {CreatePostComponent} from "./components/create-post/create-post.component";
import {MyProfileComponent} from "./components/my-profile/my-profile.component";
import {ForbiddenModalComponent} from "./components/forbidden-modal/forbidden-modal.component";


const routes: Routes = [
  { path: '', component: ViewPostsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signUp', component: SignUpComponent },
  { path: 'community/:id', component: CommunityComponent },
  { path: 'createCommunity', component: CreateCommunityComponent },
  { path: 'createPost/:id', component: CreatePostComponent },
  {path: 'myProfile', component: MyProfileComponent},
  {path: 'forbiddenModal', component: ForbiddenModalComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
