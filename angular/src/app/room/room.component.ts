import { Component, OnInit } from '@angular/core';
import {  tap } from 'rxjs/operators';
import { LogoutService } from '../services/logout.service';
import { Room } from '../model/room';
import { SaveUserAuthService } from '../services/save-user-auth.service';
import { Validators, FormControl } from '@angular/forms';
import 'rxjs/add/operator/first';
import { NzTableQueryParams} from 'ng-zorro-antd/table';
import { Router } from '@angular/router';
import * as cloneDeep from 'lodash/cloneDeep';
import * as clone from 'lodash/clone';
import * as isUndefined from 'lodash/isUndefined';
import { HttpRequestsService } from '../services/http-requests.service';
import { MessageResultService } from '../services/message-result.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']

})
export class RoomComponent implements OnInit {
  customers: any = [];
  rooms: any = [];

  editMap: Map<number, Room> = new Map<number, Room>();

  pageIndex: any;
  pageSize: any;
  total: any;
  addRoomId: any;

  addingRoom:Room;

  // temporary  input fields for edit
  formPrice: Map<number, FormControl> = new Map<number, FormControl>();
  formRoomCapacity: Map<number, FormControl> = new Map<number, FormControl>();

  loading: boolean;
  handleOkCloseDrawer: boolean;
  isVisibleAddRoom:boolean;
  userManagerAuth: boolean;
  isLoading: boolean;
  preventCall_OnQueryParams: boolean;

  constructor(
    private logoutService: LogoutService,
    private state: SaveUserAuthService,
    private router: Router,
    private httpService: HttpRequestsService,
    private messageResult: MessageResultService
  ) { }

  ngOnInit(pageInx?: any): void {

    this.userManagerAuth = false;
    const isManagerRole = 'MANAGER';

    if (this.state.userAuth.valueOf() === isManagerRole)
      this.userManagerAuth = true;

    this.loading = true;
    this.pageSize = 5;
    this.isLoading = false;
    this.isVisibleAddRoom = false;
    this.pageIndex = 1;

    if (!isUndefined(pageInx))
           this.pageIndex = pageInx;

    const pageNum = this.pageIndex - 1;

    this.httpService.getPageRooms(pageNum, this.pageSize)
      .subscribe( data => {
        this.rooms = data.rooms;
        this.total = data.count;
      },
        error => {
          console.log(error);
          this.loading = false;
          this.router.navigate(['login']);
        },
        () => {
          this.loading = false;
        }
      );
  }


  onQueryParamsChange(params: NzTableQueryParams): void {
        //** prevent execution */
    if (this.preventCall_OnQueryParams) {

    const { pageSize, pageIndex, sort, filter } = params;

    this.pageIndex = pageIndex;

    this.getPageData(pageIndex);
    return;

    }
  }

  getPageData(pageIndx: any) {
    let room: any = [];
    const pageNum = (pageIndx - 1);

    this.pageIndex = pageIndx;

    this.httpService.getPageRooms(pageNum, this.pageSize)
      .subscribe(data => { 
        room = data.rooms;
        this.total = data.count;
      },
        err => {
            console.log(err);
        },
        () => {
          this.rooms = [];
          this.rooms = cloneDeep(room);
        });
  }


  async getCurrentPage(index: any) {
    this.pageIndex = index;
    this.preventCall_OnQueryParams = true;
  }

  inputFormValidators(id: any) {
    this.formPrice.set(id, new FormControl(' ', [Validators.required, Validators.minLength(1)]));
    this.formRoomCapacity.set(id, new FormControl(' ', [Validators.required, Validators.minLength(1)]));
  }

  startEditingRoom(id: number): void {
    const room: Room = this.rooms.find(item => item.id === id);

    const roomCp = clone(room);

    this.editMap.set(id, roomCp);

    room.editRoom = true;

    this.inputFormValidators(id);
  }


  cancelEdit(id: number): void {
    const index: number = this.rooms.findIndex(item => item.id === id);
    this.rooms[index].editRoom = false;
  }


  saveAddingRoom() {
    const roomCp = clone(this.addingRoom);

    this.postRoom(roomCp);

    this.addingRoom={}
  }

  updateRoom(idRoom: number) {
    const room = this.rooms.find(item => item.id === idRoom);

    room.editRoom = false;
    const roomCp = clone(this.editMap.get(idRoom));

    // delete unnecessary for put request
    delete roomCp.editRoom;
    delete roomCp.expand;

    this.putRoom(roomCp);
  }


  startAddingRoom(): void {

    this.addingRoom={};

    this.addRoomId = 0;

    const room: Room = {
      price: '',
      roomCapacity:'',
      customers: []
    };

    this.addingRoom=room;


    this.inputFormValidators(this.addRoomId);
    this.openAddRoomModal();
  }


  logout() {
    this.logoutService.logout();
  }


  openAddRoomModal(): void {
    this.isVisibleAddRoom = true;
  }


  closeAddRoomModal(): void {
    this.isVisibleAddRoom = false;
  }


 // setters for temporary  input  edit fields
  setMapPrice(id: number, value: any) {
    const room: Room = this.editMap.get(id);
    room.price = value;
    this.editMap.set(id, room);
  }

  setMapRoomCapacity(id: number, value: any){
    const room: Room = this.editMap.get(id);
    room.roomCapacity = value;
    this.editMap.set(id, room);
  }

  putRoom(room: Room) {
    this.httpService.putRequestRoom(room)
      .subscribe(
        res => console.log(res),
        error => {
          this.messageResult.error('error updating room something went wrong');
          console.log(error);
          this.getPageData(this.pageIndex);
        },
        () => {
          this.getPageData(this.pageIndex);
          this.messageResult.success('success updating room');
        }
      );
  }


  postRoom(room: Room) {
    const firstPage = 1;
    this.httpService.postRequestRoom(room)
      .subscribe(res => { console.log(res); },
        error => {
          console.log(error);
          this.closeAddRoomModal();
          this.messageResult.error('something went wrong saving room');
        },
        () => {
          this.closeAddRoomModal();
          this.messageResult.success('success adding room');
          this.getPageData(firstPage);
        });
  }

  deleteRoom(id: number) {
    this.httpService.deleleRequestRoom(id).pipe(
      tap(() =>
       {
        const lastPage = Math.ceil((this.rooms.length / this.pageSize));
        
        const lastPageCompare = Math.ceil(((this.rooms.length - 1) / this.pageSize));

         /**  If last page has one element and gets deleted pageIndex gets correct value
              for  getPageData(this.pageIndex)
         */
        if ( (lastPage - lastPageCompare) === 1)
              this.pageIndex = this.pageIndex - 1;

      }))
      .subscribe(
        res => console.log(res),
        error => {
          console.log(error);
          this.messageResult.error('something went wrong deleting room');
        },
        () => {
          this.messageResult.success('success deleting room');
          this.getPageData(this.pageIndex);
        });
  }

}


