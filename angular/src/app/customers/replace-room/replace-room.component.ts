import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Customer } from 'src/app/model/customer';
import { CustomerDto } from 'src/app/model/customer-dto';
import { DatesUtilitiesService } from 'src/app/services/dates-utilities.service';
import * as clone from 'lodash/clone';
import { Room } from 'src/app/model/room';
import { HttpGetRequestRoomsServiceImpl } from 'src/app/services/http-request-room-impl.service';
import { MessageResultService } from 'src/app/services/message-result.service';
import { HttpRequestsService } from 'src/app/services/http-requests.service';


@Component({
  selector: 'app-replace-room',
  templateUrl: './replace-room.component.html',
  styleUrls: ['./replace-room.component.css']
})
export class ReplaceRoomComponent implements OnInit {

  @Output() reloadData = new EventEmitter<any>();
  @Input() customer:Customer;
  is_RoomBooked:boolean;

  // dropdown select room values
  roomsList: Array<{ value: any; label: string }> = [];

  selectedRoomId: any;
  replaceRoom: Room;
  isVisibleReplaceRoom: boolean;
  rooms: any = [];

  constructor( private datesUtilitiesService: DatesUtilitiesService,
               private httpRequestImplService: HttpGetRequestRoomsServiceImpl,
               private messageResult: MessageResultService,
               private httpService: HttpRequestsService,
    ) { }

  ngOnInit(): void {
    this.selectedRoomId = 0;
    this.roomsList = [];
  }


  async startReplacingCustomerRoom(customer:Customer) {
    const numberOfPersons: number = customer.persons.length;

    this.roomsList = [];
    this.roomsList  =  await this.httpRequestImplService.getAllRooms(customer.room.id, numberOfPersons);

    this.rooms = clone(this.httpRequestImplService.rooms);

    this.replaceRoom = this.rooms.find(item => item.id === customer.room.id);
    this.isVisibleReplaceRoom = true;
  }

  saveReplacingCustomerRoom() {
    const custo: CustomerDto = {
      id: this.customer.id,
      room: {
        ...this.replaceRoom
      }
    }
    this.putDtoCustomer(custo);
  }

   /**replace room for  existing customer */
  async dropDownSelectChangeRoom(id: any) {
    this.replaceRoom = {};
    this.replaceRoom = this.rooms.find(item => item.id == id);

    this.is_RoomBooked =  await this.datesUtilitiesService
                                     .findIfRoomIsBooked(id,this.customer.booking)
                                     .catch( err=> console.log(err)  );

    if (this.is_RoomBooked) {
      this.messageResult.error(' sorry this room is booked for  this dates');
      return;
    }
    this.selectedRoomId = id;
  }

  destroyModalReplaceRoom() {
     this.isVisibleReplaceRoom = false;
      this.selectedRoomId = 0;
  }

  putDtoCustomer(customer: CustomerDto) {
    this.httpService.putRequestDtoCustomer(customer)
      .subscribe(res => { console.log(res); },
        error => {
          console.log(error);
          this.messageResult.error('something went wrong saving customer');
        },
        () => {
          this.destroyModalReplaceRoom();
          this.messageResult.success('succes updating customer');
          this.reloadData.emit();
        });
  }

}
