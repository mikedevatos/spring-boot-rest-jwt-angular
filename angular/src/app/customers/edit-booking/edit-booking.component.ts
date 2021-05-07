import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Booking } from 'src/app/model/booking';
import * as clone from 'lodash/clone';
import { DatePipe } from '@angular/common';
import { DatesUtilitiesService } from 'src/app/services/dates-utilities.service';
import { MessageResultService } from 'src/app/services/message-result.service';
import { HttpRequestsService } from 'src/app/services/http-requests.service';

@Component({
  selector: 'app-edit-booking',
  templateUrl: './edit-booking.component.html',
  styleUrls: ['./edit-booking.component.css'],
})
export class EditBookingComponent implements OnInit {
  bookingEdit: Booking;
  isVisibleEditingBookings: boolean;

  @Output() reloadData = new EventEmitter<any>();

  @Input() booking: Booking;

  @Input() roomId: any;

  dateFormat = 'yyyy/MM/dd';
  dateStart = null;
  dateEnd = null;
  dateStartFormat: any;
  dateEndFormat: any;

  disableBookedDates: any[];

  startDateValidation: any;
  endDateValidation: any;

  constructor(
    private datepipe: DatePipe,
    private datesUtilitiesServ: DatesUtilitiesService,
    private messageResult: MessageResultService,
    private httpService: HttpRequestsService
  ) { }

  ngOnInit(): void {
    this.disableBookedDates = [];
    this.dateStartFormat = '';
    this.dateEndFormat = '';
  }

  onChangeEndDate(date: Date) {
    this.endDateValidation = clone(date);
    this.dateEndFormat = this.datepipe.transform(date, 'yyyy-MM-dd');
    console.log('booking  end is: ' + this.dateEndFormat);
  }

  onChangeStartDate(date: Date) {
    this.startDateValidation = clone(date);
    this.dateStartFormat = this.datepipe.transform(date, 'yyyy-MM-dd');
    console.log('booking  start  is: ' + this.dateStartFormat);
  }

  async startEditBookings(roomId: any, booking: Booking) {
    this.bookingEdit = {};

    this.bookingEdit = booking;

    const roomBookings: any[] = await await this.datesUtilitiesServ.findBookingsOfRoom(roomId,this.bookingEdit.id);

    this.disableBookedDates = await this.datesUtilitiesServ.convertBookingsToDates(roomBookings );



    this.isVisibleEditingBookings = true;
  }

  /**disable booked dates from been selected **/
  disabledDate = (current: Date): boolean => {
    for (let i of this.disableBookedDates)
      if (
        i.getDate() === current.getDate() &&
        i.getMonth() === current.getMonth()
      ) {
        return true;
      }
    return false;
  };

  destroyModalEditingBookings(): void {
    this.isVisibleEditingBookings = false;
  }

  saveEditingBookingDates() {
    this.bookingEdit.startBooking = this.dateStartFormat;
    this.bookingEdit.endBooking = this.dateEndFormat;

    if (this.startDateValidation > this.endDateValidation) {
      this.messageResult.error('sorry start  date cant be after  end date');
      return;
    }

    this.putBooking(this.bookingEdit);
  }

  putBooking(booking: Booking) {
    this.httpService.putRequestBooking(booking).subscribe(
      (res) => console.log(res),
      (error) => {
        this.messageResult.error(
          'error updating booking dates something went wrong'
        );
        console.log(error);
        this.reloadData.emit();
      },
      () => {
        this.isVisibleEditingBookings = false;
        this.reloadData.emit();
        this.messageResult.success('success updating booking dates');
      }
    );
  }
}
