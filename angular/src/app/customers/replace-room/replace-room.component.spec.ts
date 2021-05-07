import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplaceRoomComponent } from './replace-room.component';

describe('ReplaceRoomComponent', () => {
  let component: ReplaceRoomComponent;
  let fixture: ComponentFixture<ReplaceRoomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReplaceRoomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplaceRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
