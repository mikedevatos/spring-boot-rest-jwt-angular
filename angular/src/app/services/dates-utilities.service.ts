import { Injectable } from '@angular/core';
import { DatePipe } from '@angular/common';
import * as clone from 'lodash/clone';
import { Booking } from '../model/booking';
import { HttpRequestsService } from './http-requests.service';
import { tap, } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DatesUtilitiesService {
  dateToFormat = '';
  dateFormat: any = '';

  constructor(
    private datepipe: DatePipe,
    private httpService: HttpRequestsService,
  ) {
  }

async  isDateBooked(startDate: Date, stopDate: Date, date: any): Promise<boolean> {
    if (
      (date > startDate && date < stopDate)
                       ||
      ( date.getDate() === startDate.getDate() &&
        date.getMonth() === startDate.getMonth() &&
        date.getFullYear() === startDate.getFullYear() )
                       ||
      ( date.getDate() === stopDate.getDate() &&
        date.getMonth() === stopDate.getMonth() &&
        date.getFullYear() === stopDate.getFullYear() )
    )
      return true;


    return false;
  }

  /* convert booking range (start day -> end day) to dates array*/
  async convertBookingsToDates(bookings: Booking[]):Promise<any> {
  let Dates =[];
  for (let booking of bookings) {
    try
       {
         const  bookingDates  =  await this.getDates(booking.startBooking, booking.endBooking);
            Dates =[
                     ...Dates,
                     ...bookingDates
                    ];
       }
    catch (err) { console.log(err);  }
    }

    // for (let date of Dates) {
    //   this.dateFormat = this.datepipe.transform(date, 'yyyy-MM-dd');
    //   console.log(' date is : ' + this.dateFormat);
    // }
    return Dates;
  }


  async getDates(start: any, end: any) {
    /**  convert string to Date object **/
    var startArr = start.split('-');
    var endArr = end.split('-');

    var startDate = new Date();
    var endDate = new Date();

    startDate.setFullYear(startArr[0], startArr[1] - 1, startArr[2]);
    endDate.setFullYear(endArr[0], endArr[1] - 1, endArr[2]);

    console.log('start day: ', this.datepipe.transform(startDate, 'yyyy-MM-dd'));
    console.log('end day: ', this.datepipe.transform(endDate, 'yyyy-MM-dd'));

   let Dates =[]

    while (startDate <= endDate) {

      this.dateToFormat = this.datepipe.transform(startDate, 'yyyy-MM-dd');

      let date: Date = new Date();
          date = clone(startDate);

      //add day to serviceBookedDates array
       Dates = [
              ...Dates,
                 date
               ]
      //plus one day
      startDate.setDate(startDate.getDate() + 1);
    }
    return Dates;
  }


  async findIfRoomIsBooked(idRoom: number,booking:Booking):Promise<any> {
    let  _roomBookings =[];

   /**get bookings of selected  replacing or adding room  */
    await this.httpService
        .getBookingsOfRoomById(idRoom)
        .pipe(
          tap(
            data =>  { _roomBookings = data; },
            err => {console.log(err)},
            () => { }
           ) )
        .toPromise()
        .catch(err => console.log( err));

    /**  find if room is booked*/
   return  await this.isRoomBooked(_roomBookings,booking).catch(err=> console.log(err));
  }


  async isRoomBooked(bookings: Booking[],booking:Booking):Promise<boolean> {
    let is_RoomBooked:boolean =false;

    //get dates  of selected room
    const _bookedDates  =  await this.convertBookingsToDates(bookings).catch( err=> console.log(err)  );

    var startString = booking.startBooking.split('-');
    var stopString = booking.endBooking.split('-');

    var startDate = new Date();
    var stopDate = new Date();

    startDate.setFullYear(startString[0], startString[1] - 1, startString[2]);
    stopDate.setFullYear(stopString[0], stopString[1] - 1, stopString[2]);

    this.dateFormat = this.datepipe.transform(startDate, 'yyyy-MM-dd');
    console.log(' start day: ', this.dateFormat);

    this.dateFormat = this.datepipe.transform(stopDate, 'yyyy-MM-dd');
    console.log(' end day: ', this.dateFormat);

   for (let date of _bookedDates) {

      if (is_RoomBooked){
            break;
      }
        is_RoomBooked =  await this.isDateBooked(startDate,stopDate,date);
    }
    return is_RoomBooked
  }


  async findBookingsOfRoom(idRoom: number, currentIdBooking: any):Promise<any> {
     let bookings = [];

      /** get bookings of selected room*/
      await this.httpService
          .getBookingsOfRoomById(idRoom)
          .pipe(
            tap(
              data =>  { bookings = data; },
              err => {console.log(err)},
              () => { }
          ) )
          .toPromise()
          .catch(err =>console.log(err) );

    /**  filter current booking from beeing disable*/
      bookings = bookings.filter( (booking) => booking.id !== currentIdBooking );

     return bookings;
  }


}
