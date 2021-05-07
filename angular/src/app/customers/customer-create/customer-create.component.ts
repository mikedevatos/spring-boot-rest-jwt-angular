import { Person } from '../../model/person';
import {  Component, EventEmitter, OnInit, Output} from '@angular/core';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { Customer } from '../../model/customer';
import { HttpRequestsService } from '../../services/http-requests.service';
import { MessageResultService } from '../../services/message-result.service';
import { popConfirmHandleCancelService } from '../../services/modal-confirm.service';
import * as cloneDeep from 'lodash/cloneDeep';
import * as isEmpty from 'lodash/isEmpty';
import { Room } from '../../model/room';
import * as clone from 'lodash/clone';
import { take } from 'rxjs/operators';
import {BreakpointObserver} from '@angular/cdk/layout';
import * as isUndefined from 'lodash/isUndefined';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-customer-create',
  templateUrl: './customer-create.component.html',
  styleUrls: ['./customer-create.component.css'],
})
export class CustomerCreateComponent implements OnInit{

  @Output() reloadData = new EventEmitter<any>();

  persons: Person[];

  //status value for steps
  stepStatusMap: Map<number, string> = new Map<number, string>();

  //default values for select account type
  listOfItem = ['bussiness', 'vacation'];

  selectedAccountType: any;
  addRoom: Room;

  startBooking: any;
  endBooking: any;

  handleOkCloseDrawer: boolean;
  isVisibleAddCustomer: boolean;
  addCustomerRecord: boolean;

  current: number;
  isPersonValid:boolean;

  stepsDirection:any;

  dateFormat: any = '';

  constructor(
    private datepipe: DatePipe,
    private modal: NzModalService,
    private popConfirm: popConfirmHandleCancelService,
    private httpService: HttpRequestsService,
    private messageResult: MessageResultService,
    private resolutionObserve:BreakpointObserver
  ) { }

  ngOnInit(): void {
    this.resolutionObserve.observe( ['(max-width: 700px)'])
                          .subscribe(result =>
                           {
                             result.matches ?  this.stepsDirection="vertical" : this.stepsDirection ="horizontal";
                           });

    this.initializeValues();
  }


  initializeValues() {
    this.persons=[]
    this.current = 0;
    this.selectedAccountType = '';
    this.startBooking = '';
    this.endBooking = '';
    this.addRoom={};
  }

  onChangeStartDate(startDate:any){
    this.startBooking=startDate;
    this.dateFormat = this.datepipe.transform(this.startBooking, 'yyyy-MM-dd');
    console.log(' start date is : ' + this.dateFormat);
    this.addRoom={};
  }

  onChangeEndDate(endDate:any){
    this.endBooking=endDate;
    this.dateFormat = this.datepipe.transform(this.endBooking, 'yyyy-MM-dd');
    console.log(' end date is : ' + this.dateFormat);
    this.addRoom={};
  }

  onChangePersonValidation(value:boolean){
   this.isPersonValid=value;
   }


   onChangePersons(persons:Person[]){
     this.persons=[...persons];
   }


   onRoomChange(room:Room){
    this.addRoom =clone(room);
   }

  selectAccountTypeOnChange(value: any) {
    this.selectedAccountType = value;
  }

  async cancelAddCustomer() {
    const ref: NzModalRef = this.modal.confirm({
      nzTitle: 'Are you sure you want to cancel adding new customer?',
      nzOnOk: () => this.popConfirm.popUpDialog(true),
      nzOnCancel: () => this.popConfirm.popUpDialog(false),
    });

    this.handleOkCloseDrawer = await this.popConfirm.confirm
                                         .pipe(take(1))
                                         .toPromise();
    if (this.handleOkCloseDrawer) {
      this.closeDrawer();
      this.addRoom = {};
    }
  }

  async startAddingCustomer() {

    if( isUndefined(this.persons) ){

      this.persons = [];

      const custo: Customer = {
        bill: '0.0',
        booking: {
          startBooking: '',
          endBooking: '',
        },
        persons: [],
        room: {
          price: '',
          roomCapacity: '',
        },
      };

      this.persons = cloneDeep(custo);
    }

    this.stepStatusMap.set(0, 'process');
    this.stepStatusMap.set(1, 'wait');
    this.stepStatusMap.set(2, 'wait');
    this.stepStatusMap.set(3, 'wait');

    this.isVisibleAddCustomer = true;
    this.addCustomerRecord = true;
  }

  closeDrawer(): void {
    this.isVisibleAddCustomer = false;
    this.addCustomerRecord = false;
    this.initializeValues()
  }

  onStepChange() {
    this.stepStatusMap.set(this.current, 'process');
  }

  previus(): void {
    this.current -= 1;
    this.stepStatusMap.set(this.current, 'process');
  }

  personValidation() :boolean{
    if(!this.isPersonValid){
      this.messageResult.error("sorry   fix errors in person");
        this.stepStatusMap.set(0, 'error');
        return false;
    }
    return true;
  }


  bookingValidation() : boolean{
    if(  this.startBooking > this.endBooking ){
        this.messageResult.error("sorry start  date cant be after  end date");
        this.stepStatusMap.set(1, 'error');
        return false;
    }
    return true;
  }


  addRoomValidation() :boolean{
    if ( isEmpty(this.addRoom) ) {
      this.stepStatusMap.set(2, 'error');
      this.messageResult.error("sorry you haven't add a room");
      return false;
    }
    return true;
  }

  getAccountTypeValidation(): boolean {
    const num = this.selectedAccountType.length;
    if(num < 3){
      this.messageResult.error("sorry  you must select account type");
      this.stepStatusMap.set(3, 'error');
      return false;
    }
    return  true;
  }


  next(): void {
    if(this.current===0 && !this.personValidation())
             return

    else if( this.current===1 && !this.bookingValidation() )
              return;

    else if( this.current===2  &&  !this.addRoomValidation() )
                 return;

    else if(this.current===3  && !this.getAccountTypeValidation() )
                  return;

    this.stepStatusMap.set(this.current, 'finish');

    this.current += 1;
  }

  saveCustomer() {
    const persons = cloneDeep(this.persons);

    //delete person id
    persons.map((person) => {
      delete person.id;
    });

    const custo: Customer = {
      bill: 0,
      accountType: this.selectedAccountType,
      booking: {
        startBooking: this.startBooking,
        endBooking: this.endBooking,
      },
      persons: [...persons],
      room: {
        ...this.addRoom,
      },

    };

    this.postCustomer(custo);
  }

  postCustomer(customer: Customer) {
    const firstPage = 1;
    this.httpService.postRequestCustomer(customer).subscribe(
      (res) => {
        console.log(res);
      },
      (error) => {
        console.log(error);
        this.stepStatusMap.set(0, 'wait');
        this.stepStatusMap.set(1, 'wait');
        this.stepStatusMap.set(2, 'wait');
        this.stepStatusMap.set(3, 'wait');
        this.stepStatusMap.set(4, 'error');

        this.messageResult.error('sorry something went wrong ');
      },
      () => {
        this.addCustomerRecord = false;
        this.isVisibleAddCustomer = false;
        this.initializeValues();
        this.messageResult.success('success saving customer');
        this.reloadData.next(firstPage);

      }
    );
  }
}
