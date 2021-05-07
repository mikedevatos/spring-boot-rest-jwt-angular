import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectBookingComponent } from './select-booking.component';

describe('SelectBookingComponent', () => {
  let component: SelectBookingComponent;
  let fixture: ComponentFixture<SelectBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
