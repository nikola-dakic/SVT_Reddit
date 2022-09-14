import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RouterModule} from "@angular/router";
import {NavBarComponent} from './components/nav-bar/nav-bar.component';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {PostComponent} from './components/post/post.component';
import {VoteButtonComponent} from './components/vote-button/vote-button.component';
import {CommunityComponent} from './components/community/community.component';
import {ViewPostsComponent} from './components/view-posts/view-posts.component';
import {PostService} from "./services/post.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import { PostInCommunityComponent } from './components/post-in-community/post-in-community.component';
import { CreateCommunityComponent } from './components/create-community/create-community.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommunityService} from "./services/community.service";
import {UserService} from "./services/user.service";
import {NgbDropdownModule, NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {AuthService} from "./services/auth.service";
import {TokenInterceptor} from "./interceptor/TokenInterceptor";
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { ForbiddenModalComponent } from './components/forbidden-modal/forbidden-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    SignUpComponent,
    PostComponent,
    VoteButtonComponent,
    CommunityComponent,
    ViewPostsComponent,
    PostInCommunityComponent,
    CreateCommunityComponent,
    CreatePostComponent,
    MyProfileComponent,
    ForbiddenModalComponent,

  ],
    imports: [
        BrowserModule,
        CommonModule,
        AppRoutingModule,
        RouterModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgbDropdownModule
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    PostService,
    CommunityService,
    UserService,
    NgbModalConfig,
    NgbModal,
    AuthService,

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
