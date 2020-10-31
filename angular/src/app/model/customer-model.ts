import { RoomsModel } from './rooms-model';


export class CustomerModel {
    public id?: number;
    public firstName?: string;
    public lastName?: string;
    public email?: string;
    public bill?: any;
    public rooms?: RoomsModel[];
    public expand?: boolean;
    public edit?: boolean;
    public count?: number;






}

