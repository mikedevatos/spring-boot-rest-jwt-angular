import { Person } from "./person";
import { Room } from "./room";

export interface CustomerDto {
    id?:number;
    bill?: any;
    accountType?:any;
    person?:Person;
    room?: Room;
}
