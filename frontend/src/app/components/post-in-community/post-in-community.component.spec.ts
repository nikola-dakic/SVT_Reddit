import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PostInCommunityComponent} from './post-in-community.component';

describe('PostInCommunityComponent', () => {
  let component: PostInCommunityComponent;
  let fixture: ComponentFixture<PostInCommunityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostInCommunityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostInCommunityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
