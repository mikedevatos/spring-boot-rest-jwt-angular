import { Room } from './room';
import { Person } from './person';

import { Booking } from './booking';



export interface Customer {
    id?: number;
    bill?: any;
    accountType?:any;
     booking?:Booking;
     persons?:Person[];
     room?: Room;
    expand?: boolean;
    edit?: boolean;
     count?: number;
  showPersons?:boolean;

}
