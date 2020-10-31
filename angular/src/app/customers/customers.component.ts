import { Component, OnInit } from '@angular/core';
import { take, tap } from 'rxjs/operators';
import { CustomerModel } from '../model/customer-model';
import { LogoutService } from '../services/logout.service';
import { RoomsModel } from '../model/rooms-model';
import { SaveUserAuthService } from '../services/save-user-auth.service';
import { Validators, FormControl } from '@angular/forms';
import 'rxjs/add/operator/first';
import { NzModalService} from 'ng-zorro-antd/modal';
import { NzModalRef} from 'ng-zorro-antd/modal';
import { NzTableQueryParams} from 'ng-zorro-antd/table';

import { popConfirmHandleCancelService } from '../services/modal-confirm.service';
import { Router } from '@angular/router';
import * as cloneDeep from 'lodash/cloneDeep';
import * as clone from 'lodash/clone';
import * as isUndefined from 'lodash/isUndefined';
import * as isEmpty from 'lodash/isEmpty';
import { HttpRequestsService } from '../services/http-requests.service';
import { MessageResultService } from '../services/message-result.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css'],
  styles: [
    `
      nz-select {
        width: 100%;
      }
    `
  ]
})

export class CustomersComponent implements OnInit {
  customers: any = [];
  rooms: any = [];

  addRooms: RoomsModel[];
  editMap: Map<number, CustomerModel> = new Map<number, CustomerModel>();
  roomsList: Array<{ value: any; label: string }> = [];
  customerHelperRefer: CustomerModel;

  pageIndex: any;
  pageSize: any;
  total: any;
  addCustomerId: any;



  selectedMultipleRoomId: any;
  selectedSingleRoomId: any;



  // temporary  input fields for edit
  formEmail: Map<number, FormControl> = new Map<number, FormControl>();
  formLastName: Map<number, FormControl> = new Map<number, FormControl>();
  formFirstName: Map<number, FormControl> = new Map<number, FormControl>();
  formBill: Map<number, FormControl> = new Map<number, FormControl>();
  formPrice: Map<number, FormControl> = new Map<number, FormControl>();

  addCustomerRecord: boolean;
  loading: boolean;
  handleOkCloseDrawer: boolean;
  visibleDrawer: boolean;
  isVisibleModal: boolean;
  userManagerAuth: boolean;
  preventCalledOnInit: boolean;
  isLoading: boolean;




  constructor(
    private modal: NzModalService,
    private popConfirm: popConfirmHandleCancelService,
    private logoutService: LogoutService,
    private state: SaveUserAuthService,
    private router: Router,
    private httpService: HttpRequestsService,
    private messageResult: MessageResultService
  ) {

  }

  ngOnInit(pageInx?: any): void {

    this.userManagerAuth = false;
    const isManagerRole = 'MANAGER';

    if (this.state.userAuth.valueOf() === isManagerRole) {

      this.userManagerAuth = true;
    }
    this.loading = true;
    this.pageSize = 3;
    this.selectedSingleRoomId = [];
    this.selectedMultipleRoomId = [];
    this.isLoading = false;
    this.addRooms = [];
    this.isVisibleModal = false;

    this.visibleDrawer = false;
    this.pageIndex = 1;
    if (!(isUndefined(pageInx))) {
      this.pageIndex = pageInx;
    }

    const pageNum = this.pageIndex - 1;
    this.httpService.getCustomers(pageNum, this.pageSize)
      .subscribe(data => {
        this.customers = data.customer;
        this.total = data.count;
      },
        error => {

          console.log(error);
          this.loading = false;
          this.router.navigate(['login']);


        },
        () => {
          this.loading = false;
          this.preventCalledOnInit = true;

        }
      );


  }





  onQueryParamsChange(params: NzTableQueryParams): void {
    // prevent execution of ngOnInit()
    if (isUndefined(this.preventCalledOnInit)) {
      return;
    }
    const { pageSize, pageIndex, sort, filter } = params;

    this.pageIndex = pageIndex;

    this.getPageData(pageIndex);

  }


  getPageData(pageIndx: any) {

    let custo: any = [];
    const pageNum = (pageIndx - 1);

    this.pageIndex = pageIndx;

    this.httpService.getCustomers(pageNum, this.pageSize)
      .subscribe(data => {
        custo = data.customer;
        this.total = data.count;
      },
        err => {
          console.log(err);
        },
        () => {
          this.customers = [];
          this.customers = cloneDeep(custo);

        });

  }



  inputFormValidators(id: any) {
    this.formEmail.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
    this.formFirstName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
    this.formLastName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
    this.formBill.set(id, new FormControl(' ', [Validators.required, Validators.minLength(1)]));
  }


  startEditCustomer(id: any): void {

    const custo: CustomerModel = this.customers.find(item => item.id === id);

    const customer = cloneDeep(custo);

    this.editMap.set(id, customer);

    custo.edit = true;

    this.inputFormValidators(id);
  }



  startEditRoom(idCustomer: any, idRoom: any): void {

    const custo = this.customers.find(item => item.id === idCustomer);

    const room: RoomsModel = custo.rooms.find(item => item.id === idRoom);

    const customer = cloneDeep(custo);

    this.editMap.set(idCustomer, customer);

    this.formPrice.set(idRoom, new FormControl(' ', [Validators.required, Validators.minLength(1)]));

    room.editRoom = true;

  }




  cancelEditRoom(idCustomer: any, idRoom: any): void {

    const index: number = this.customers.findIndex(item => item.id === idCustomer);

    const room: RoomsModel = this.customers[index].rooms.find(item => item.id === idRoom);

    room.editRoom = false;

  }

  cancelEdit(id: number): void {
    const index: number = this.customers.findIndex(item => item.id === id);
    this.customers[index].edit = false;

  }


  updateRoom(idCustomer: number, idRoom: number) {

    const customer = this.customers.find(item => item.id === idCustomer);

    const index: number = customer.rooms.findIndex(item => item.id === idRoom);

    customer.rooms[index].editRoom = false;
    customer.rooms[index].price =    this.editMap.get(idCustomer).rooms[index].price;

    const customerCopy = cloneDeep(this.editMap.get(idCustomer));

    this.putCustomer(customerCopy);
  }



  addCustomer(idEditMap: number) {
    const customer = this.editMap.get(idEditMap) as CustomerModel;

    // if rooms have been selected
    if (!isEmpty(this.addRooms)) {

      customer.rooms = clone(this.addRooms);
    }

    const customerCopy = cloneDeep(customer);

    this.postCustomer(customerCopy);
    this.selectedMultipleRoomId = [];
    this.addRooms = [];

    this.addCustomerRecord = false;
  }



  updateCustomer(idCustomer: number) {

    const custo = this.customers.find(item => item.id === idCustomer);


    Object.assign(custo, this.editMap.get(idCustomer));


    custo.edit = false;


    const customerCp = cloneDeep(this.editMap.get(idCustomer));

    // delete unnecessary for put request
    delete customerCp.edit;
    delete customerCp.expand;

    this.putCustomer(customerCp);

  }



 saveRoom() {

    const room: RoomsModel = this.addRooms.find(item => item.id === this.selectedSingleRoomId);

    // check if room already exists
    if (this.customerHelperRefer.rooms.find(item => item.id === room.id)) {

      const errorMessage = 'Room allready exists can not be saved';
      this.messageResult.error(errorMessage);

      return;
    }


    const roomCp = clone(room);
    this.customerHelperRefer.rooms = [
      ...this.customerHelperRefer.rooms,
      roomCp
    ];

    // get customer that room is assosiate with
    const customerCopy: CustomerModel = cloneDeep(this.customerHelperRefer);

    /// delete unessesary for put request
    delete customerCopy.edit;
    delete customerCopy.expand;

    this.putCustomer(customerCopy);

    this.customerHelperRefer.expand = true;

    this.destroyModal();
  }


  startAddingRoom(idCustomer: number): void {
    this.getAllRooms();
    const customer: CustomerModel = this.customers.find(item => item.id === idCustomer);

    this.customerHelperRefer = customer;
  }



  startAddingCustomer(): void {

    const id = 0;
    this.addCustomerId = id;

    const custo: CustomerModel = {
      firstName: '',
      lastName: '',
      email: '',
      bill: '',
      rooms: []
    };
    const custoCopy = cloneDeep(custo);

    this.editMap.set(id, custoCopy);

    this.addCustomerRecord = true;

    this.inputFormValidators(id);

    this.getAllRooms();

    this.openDrawer();
  }


  async cancelAddCustomer() {

    const ref: NzModalRef = this.modal.confirm({
      nzTitle: 'Are you sure you want to cancel adding new customer?',
      nzOnOk: () => this.popConfirm.popUpDialog(true),
      nzOnCancel: () => this.popConfirm.popUpDialog(false)

    });

    this.handleOkCloseDrawer = await this.popConfirm.confirm.pipe(take(1)).toPromise();
    if (this.handleOkCloseDrawer) {
      this.closeDrawer();
      this.selectedMultipleRoomId = [];
      this.addRooms = [];
    }
  }



  getAllRooms(): void {

    /// check if rooms already populated
    if (!isEmpty(this.rooms)) {

      return;
    }

    this.httpService.getRequestRoom()
      .subscribe(data => {
        this.rooms = data;
      },
        error => { console.log(error); },

        () => {
          this.roomsList = this.rooms.map(item => {

             /// object immutability
            const roomCp = { ...item };

            return {
              value: roomCp.id,
              label: roomCp.id.toString()
            };
          });
        });
  }




  logout() {
    this.logoutService.logout();
  }




  async getCurrentPage(index: any) {
    this.pageIndex = index;
  }





  createModal() {
    this.isVisibleModal = true;
  }


  destroyModal(): void {
    this.isVisibleModal = false;
    this.selectedSingleRoomId = [];
    this.addRooms = [];

  }



  dropDownSelectMultiple(id: any[]) {

    id.join('');

    const filterRooms: RoomsModel[] = this.rooms.filter(room => id.find(item => item === room.id));

    this.selectedMultipleRoomId = id;

    this.addRooms = [];
    this.addRooms = filterRooms;
  }



  dropDownSelectSingle(id: any) {
    this.selectedSingleRoomId = [];

    this.addRooms = [];

    const filterRooms: RoomsModel = this.rooms.find(item => item.id === id);

    this.selectedSingleRoomId = id;

    this.addRooms.push(filterRooms);
  }



  openDrawer(): void {
    this.visibleDrawer = true;
  }

  closeDrawer(): void {
    this.visibleDrawer = false;
    this.addCustomerRecord = false;
  }






 // setters for temporary  input  edit fields
  setMapFirstName(id: number, value: any) {
    const custo: CustomerModel = this.editMap.get(id);
    custo.firstName = value;
    this.editMap.set(id, custo);
  }
  setMapLastName(id: number, value: any) {
    const custo: CustomerModel = this.editMap.get(id);
    custo.lastName = value;
    this.editMap.set(id, custo);
  }
  setMapEmail(id: number, value: any) {
    const custo: CustomerModel = this.editMap.get(id);
    custo.email = value;
    this.editMap.set(id, custo);
  }
  setMapBill(id: number, value: any) {
    const custo: CustomerModel = this.editMap.get(id);
    custo.bill = value;
    this.editMap.set(id, custo);
  }
  setMapRoomPrice(idCustomer: number, indexRoom: number, value: any) {
    const custo: CustomerModel = this.editMap.get(idCustomer);
    const customer = cloneDeep(custo);

    customer.rooms[indexRoom].price = value;

    this.editMap.set(idCustomer, customer);
  }



  putCustomer(customer: CustomerModel) {
    this.httpService.putReqCustomer(customer)
      .subscribe(
        res => console.log(res),

        error => {
          this.messageResult.error('error updating customer record something went wrong');
          console.log(error);
          this.getPageData(this.pageIndex);

        },

        () => {

          // this.getPageData(this.pageIndex);

          this.messageResult.success('success updating customer');

        }
      );
  }


  postCustomer(customer: CustomerModel) {
    const firstPage = 1;
    this.httpService.postReqCustomer(customer)
      .subscribe(res => { console.log(res); },

        error => {
          console.log(error);
          this.messageResult.error('something went wrong saving customer');
        },
        () => {
          this.messageResult.success('success adding customer');
          this.getPageData(firstPage);
        });
  }



  deleteRoom(idCustomer: number, idRoom: number) {
    this.httpService.deleleReqRoom(idCustomer, idRoom)
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


  deleteCustomer(id: number) {
    this.httpService.deleteReqCustomer(id).pipe(
      tap(() =>

       {

        const lastPage = Math.ceil((this.customers.length / this.pageSize));
        const lastPageCompare = Math.ceil(((this.customers.length - 1) / this.pageSize));


         /**  If last page has one element and gets deleted pageIndex gets correct value
              for  getPageData(this.pageIndex)
         */
        if ((lastPage - lastPageCompare) === 1) {

          this.pageIndex = this.pageIndex - 1;
        }

      }))
      .subscribe(

        res => console.log(res),

        error => {

          console.log(error);

          this.messageResult.error('something went wrong deleting customer');

        },


        () => {

          this.messageResult.success('success deleting customer');
          this.getPageData(this.pageIndex);

        });
  }

}




