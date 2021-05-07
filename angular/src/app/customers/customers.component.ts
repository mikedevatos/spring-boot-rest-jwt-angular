import { CustomerPutDto } from './../model/customer-put-dto';
import { Component, OnInit} from '@angular/core';
import { tap } from 'rxjs/operators';
import { Customer } from '../model/customer';
import { LogoutService } from '../services/logout.service';
import { SaveUserAuthService } from '../services/save-user-auth.service';
import { Validators, FormControl } from '@angular/forms';
import 'rxjs/add/operator/first';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { Router } from '@angular/router';
import * as cloneDeep from 'lodash/cloneDeep';
import * as isUndefined from 'lodash/isUndefined';
import { HttpRequestsService } from '../services/http-requests.service';
import { MessageResultService } from '../services/message-result.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css'],
  styles: [
    `  nz-select {  width: 100%;}  `
  ],
  providers: [DatePipe]
})

export class CustomersComponent implements OnInit {

  customers: any = [];

  /**temporally edit input fields for customer*/
  editMap: Map<number, Customer> = new Map<number, Customer>();

  pageIndex: any;
  pageSize: any;
  total: any;


  // edit validation for  person input fields
  formBill: Map<number, FormControl> = new Map<number, FormControl>();
  formAccountType: Map<number, FormControl> = new Map<number, FormControl>();
  formPrice: Map<number, FormControl> = new Map<number, FormControl>();

  loading: boolean;

  userManagerAuth: boolean;
  preventCall_OnQueryParams: boolean;
  isLoading: boolean;
  idCustomer:number;

  constructor(
    private logoutService: LogoutService,
    private userAuthState: SaveUserAuthService,
    private router: Router,
    private httpService: HttpRequestsService,
    private messageResult: MessageResultService
  ) { }

  ngOnInit(page?: any): void {
    const isManagerRole = 'MANAGER';

    if (this.userAuthState.userAuth.valueOf() === isManagerRole) {
      this.userManagerAuth = true;
    }

    this.loading = true;
    this.pageSize = 5;
    this.isLoading = false;
    this.pageIndex = 1;

    if ( !(isUndefined(page)) ) {
         this.pageIndex = page;
    }

    const pageNum = this.pageIndex - 1;

    this.httpService.getRequestCustomers(pageNum, this.pageSize)
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
        }
      );
  }


  /**temporally edit input fields for customer*/
  setMapBill(id: number, value: any) {
    const custo: Customer = this.editMap.get(id);
    custo.bill = value;
    this.editMap.set(id, custo);
  }

  setMapAccountType(id: number, value: any) {
    const custo: Customer = this.editMap.get(id);
    custo.accountType = value;
    this.editMap.set(id, custo);
  }

  inputFormValidatorsCustomer(id: any) {
    this.formBill.set(id, new FormControl(' ', [Validators.required, Validators.minLength(1)]));
    this.formAccountType.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
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

  getPageData(index?: any) {
    let custo: any = [];
    let pageIndex = 0;

    if(isUndefined(index)){
      pageIndex = (this.pageIndex - 1);
    }
    else{
      pageIndex = (index - 1);
      this.pageIndex = index;
    }

    this.httpService
        .getRequestCustomers(pageIndex, this.pageSize)
        .subscribe(
                   data => {
                           custo = data.customer;
                           this.total = data.count;
                           },
                   err => { console.log(err); },
                   () => {
                             this.customers = [];
                             this.customers = cloneDeep(custo);
                           });
  }

   async getCurrentPage(index: any) {
    this.pageIndex = index;
    this.preventCall_OnQueryParams = true;
  }

  logout() {
    this.logoutService.logout();
  }

  startEditingCustomer(id: any): void {

    const custo: Customer = this.customers.find(item => item.id === id);

    const customer = cloneDeep(custo);

    this.editMap.set(id, customer);

    custo.edit = true;
    this.inputFormValidatorsCustomer(id);
  }

  cancelEditCustomer(id: number): void {
    const index: number = this.customers.findIndex(item => item.id === id);
    this.customers[index].edit = false;
  }

  updateCustomer(idCustomer: number) {
    const customerDTO:CustomerPutDto={
      id:this.editMap.get(idCustomer).id,
      bill:this.editMap.get(idCustomer).bill,
      accountType:this.editMap.get(idCustomer).accountType
    };

    this.putCustomer(customerDTO);
  }

  deleteCustomer(id: number) {
    this.httpService.deleteRequestCustomer(id).pipe(
      tap(() => {
        const lastPage = Math.ceil((this.customers.length / this.pageSize));
        const lastPageCompare = Math.ceil(((this.customers.length - 1) / this.pageSize));

        /**  If last page has one element and gets deleted pageIndex gets correct value
             for  getPageData(this.pageIndex)
        */
        if ((lastPage - lastPageCompare) === 1) {
          this.pageIndex = this.pageIndex - 1;
        }

      }))
      .subscribe(res => console.log(res),
        error => {
          console.log(error);
          this.messageResult.error('something went wrong deleting customer');
        },
        () => {
          this.messageResult.success('success deleting customer');
          this.getPageData(this.pageIndex);
        });
  }

  putCustomer(customer: Customer) {
    this.httpService.putRequestCustomer(customer)
      .subscribe(
        res => console.log(res),
        error => {
          this.messageResult.error('error updating customer record something went wrong');
          console.log(error);
          this.getPageData(this.pageIndex);
        },
        () => {
          this.getPageData(this.pageIndex);
          this.messageResult.success('success updating customer');
        });
  }
}




