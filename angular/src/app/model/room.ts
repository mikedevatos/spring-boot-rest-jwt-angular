
import { Customer } from './customer';

export interface Room {

    id?: number;
    price?: any;
    roomCapacity?:any;
    editRoom?: boolean;
    customers?: Customer[];
 }
