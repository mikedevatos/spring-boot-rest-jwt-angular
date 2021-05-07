import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as clone from 'lodash/clone';

@Component({
  selector: 'app-select-booking',
  templateUrl: './select-booking.component.html',
  styleUrls: ['./select-booking.component.css']
})
export class SelectBookingComponent implements OnInit {

  @Output() emitStartDate = new EventEmitter<any>();
  @Output() emitEndDate = new EventEmitter<any>();
  @Input() start:any;
  @Input() end:any;

  dateFormat = 'yyyy/MM/dd';

  dateStart = null;
  dateEnd = null;

  startBooking: any;
  endBooking: any;

  startDateValidation: any;
  endDateValidation: any;

  constructor(private datepipe: DatePipe) { }

  ngOnInit(): void {

    if( !(this.start.length < 2 || this.end.length <2 )){

      var startArr = this.start.split('-');
      var endArr = this.end.split('-');

      this.dateStart = new Date();
      this.dateEnd= new Date();

      this.dateStart.setFullYear(startArr[0], startArr[1] - 1, startArr[2]);
      this.dateEnd.setFullYear(endArr[0], endArr[1] - 1, endArr[2]);
    }
    this.startBooking = '';
    this.endBooking = '';
  }

 async onChangeEndDate(date: Date) {

    this.endDateValidation = clone(date);
    this.endBooking = this.datepipe.transform(date, 'yyyy-MM-dd');
    await this.emitEndDate.emit(this.endBooking);
  }

 async onChangeStartDate(date: Date) {

    this.startDateValidation = clone(date);
    this.startBooking = this.datepipe.transform(date, 'yyyy-MM-dd');
    await this.emitStartDate.emit(this.startBooking);
  }


}
