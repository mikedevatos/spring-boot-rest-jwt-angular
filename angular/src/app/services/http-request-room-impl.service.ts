import { Room } from '../model/room';
import { Injectable } from '@angular/core';
import { take } from 'rxjs/operators';
import * as cloneDeep from 'lodash/cloneDeep';
import { HttpRequestsService } from './http-requests.service';
import * as isUndefined from 'lodash/isUndefined';


@Injectable({
  providedIn: 'root'
})
export class HttpGetRequestRoomsServiceImpl {
  roomList: Array<{ value: any; label: string }> = [];
  rooms: any = [];

  constructor( private httpService: HttpRequestsService,) { }


 isRoomCapacitySurpassed(room:Room,numberOfPersons?:number):boolean{

     if( !isUndefined(numberOfPersons) ){

          if( room.roomCapacity < numberOfPersons )
              return true;
     }

     return false;
 }

 async  getAllRooms(idCurrentRoom?:any,numberOfPersons?:number):Promise<any> {

   this.roomList=[];
   this.rooms=[];

    try{
      await (await  this.httpService.getRequestRooms()
             .pipe(take(1))
             .toPromise()
             .then (  r => {
               this.rooms=r;

               this.roomList =      this.rooms
                                     .filter( item=> !(this.isRoomCapacitySurpassed(item,numberOfPersons)))
                                     .map(item => {
                /// object immutability
                const roomCp = { ...item };

                return {
                  value: roomCp.id,
                  label: roomCp.id.toString()
                };
              });

          /*filter from array current room  in replacing room mode*/
            if( !isUndefined(idCurrentRoom)  ){

            const roomCP = this.roomList.filter(item => item.label != idCurrentRoom);

            this.roomList = [];
            this.roomList = cloneDeep(roomCP);

            }
            }  )   ) }
            catch(error){ console.log(error); }

      return this.roomList;
    }
  }






