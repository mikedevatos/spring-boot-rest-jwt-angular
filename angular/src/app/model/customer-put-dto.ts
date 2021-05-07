import { Room } from './room';

export interface CustomerPutDto {
    id?: number;
    bill?: any;
    accountType?:any;
    room?:Room;
}
