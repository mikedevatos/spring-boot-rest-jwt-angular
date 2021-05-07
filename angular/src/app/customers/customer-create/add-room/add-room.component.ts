import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Booking } from 'src/app/model/booking';
import { Room } from 'src/app/model/room';
import { DatesUtilitiesService } from 'src/app/services/dates-utilities.service';
import * as clone from 'lodash/clone';
import { MessageResultService } from 'src/app/services/message-result.service';
import { HttpGetRequestRoomsServiceImpl } from 'src/app/services/http-request-room-impl.service';
import * as cloneDeep from 'lodash/cloneDeep';
import * as isEmpty from 'node_modules/lodash/isEmpty';

@Component({
  selector: 'app-add-room',
  templateUrl: './add-room.component.html',
  styleUrls: ['./add-room.component.css']
})
export class AddRoomComponent implements OnInit {

  @Input() dateStart: any;
  @Input() dateEnd: any;
  @Input() room:Room;
  @Output() emitRoom = new EventEmitter<Room>();

  isAddRoomVisible:boolean;

  // dropdown select room values
  roomsList: Array<{ value: any; label: string }> = [];

  rooms: any = [];
  addRoom: Room;

  selectedRoomId: any;

  is_RoomBooked:boolean;

  constructor( private datesUtilitiesService: DatesUtilitiesService,
    private messageResult: MessageResultService,
    private httpRequestImplService: HttpGetRequestRoomsServiceImpl,
    ) { }

  async ngOnInit(): Promise<void> {

    if( !isEmpty(this.room) ){
      this.addRoom={};

      this.addRoom=clone(this.room);

      this.selectedRoomId=this.addRoom.id;
    }
    else
    {
      this.addRoom={};
    }

    await  this.getRooms();
    this.isAddRoomVisible=true;
  }


 async  getRooms(){
    this.roomsList= await this.httpRequestImplService.getAllRooms();
    this.rooms = cloneDeep(this.httpRequestImplService.rooms);
  }

  async dropdownSelectAddRoom(id: any) {

    const booking: Booking = {
      id: 0,
      startBooking: this.dateStart,
      endBooking: this.dateEnd,
    };

    this.is_RoomBooked =  await this.datesUtilitiesService.findIfRoomIsBooked(id,booking);

    this.selectedRoomId = [];
    this.addRoom = {};

    if (this.is_RoomBooked) {
      this.messageResult.error('sorry cant add room is booked for this dates');
      return;
    }

    const room: Room = this.rooms.find(item => item.id === id);

    this.selectedRoomId = id;
    this.addRoom = clone(room);

   await this.emitRoom.emit(this.addRoom);
  }


}
