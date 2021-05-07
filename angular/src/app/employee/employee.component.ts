import { Component, OnInit } from '@angular/core';
import { Employee } from '../model/employee';
import { Router } from '@angular/router';
import { LogoutService } from '../services/logout.service';
import { tap, take } from 'rxjs/operators';
import { FormControl, Validators } from '@angular/forms';
import { NzModalService} from 'ng-zorro-antd/modal';
import { NzModalRef} from 'ng-zorro-antd/modal';
import { NzTableQueryParams} from 'ng-zorro-antd/table';
import { popConfirmHandleCancelService } from '../services/modal-confirm.service';
import * as cloneDeep from 'lodash/cloneDeep';
import { HttpRequestsService } from '../services/http-requests.service';
import * as isUndefined from 'lodash/isUndefined';
import { MessageResultService } from '../services/message-result.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {

  employees: any = [];
  editMap: Map<number, Employee> = new Map<number, Employee>();

  pageSize: any;
  total: any;
  pageIndex: any;
  addEmployeeId: any;

    // temporary  input fields for edit validation
  formUsername: Map<number, FormControl> = new Map<number, FormControl>();
  formPassword: Map<number, FormControl> = new Map<number, FormControl>();
  formFirstName: Map<number, FormControl> = new Map<number, FormControl>();
  formLastName: Map<number, FormControl> = new Map<number, FormControl>();

  handleOkDrawer: boolean;
  loading: boolean;
  addEmployeRecord: boolean;
  visibleDrawer: boolean;
  preventCalledngOnInit: boolean;
  iSPasswordModalVisible: boolean;
  updatedPasswordId:number;

  constructor(
    private httpService: HttpRequestsService,
    private router: Router,
    private messageResult: MessageResultService,
    private logoutService: LogoutService,
    private popConfirm: popConfirmHandleCancelService,
    private modal: NzModalService
  ) { }

  ngOnInit(pageInx?: any) {

    this.loading = true;
    this.visibleDrawer = false;
    this.pageSize = 4;
    this.iSPasswordModalVisible=false;
    this.pageIndex = 1;

    if ( !isUndefined(pageInx) )
      this.pageIndex = pageInx;


    const pageNum = this.pageIndex - 1;

    this.httpService.getRequestEmployees(pageNum, this.pageSize)
        .subscribe(
          data => {
                   this.employees = data.employees;
                   this.total = data.count;
        },
        error => {
          this.loading = false;
          console.log(error);
          this.router.navigate(['login']);
        },
        () => {
          this.loading = false;
          this.preventCalledngOnInit = true;
        }
      );
  }

  onQueryParamsChange(params: NzTableQueryParams): void {

    // prevent execution of ngOnInit()
    if ( isUndefined(this.preventCalledngOnInit) )
                 return;

    const { pageSize, pageIndex, sort, filter } = params;
    this.pageIndex = pageIndex;
    this.getPageData(pageIndex);
  }


  async getCurrentPage(index: any) {
    this.pageIndex = index;
  }

  getPageData(pageIndx: any) {
    let emp: any = [];
    const pageNum = (pageIndx - 1);

    this.pageIndex = pageIndx;

    this.httpService.getRequestEmployees(pageNum, this.pageSize)
      .subscribe(data => {
        emp = data.employees;
        this.total = data.count;
      },
        err => {
          console.log(err);
        },
        () => {
          this.employees = [];
          this.employees = cloneDeep(emp);
        });
  }


  inputFormValidators(id: any) {
    this.formUsername.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4) ]));
    this.formFirstName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4) ]));
    this.formPassword.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4) ]));
    this.formLastName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4) ]));
  }


  startEdit(id: any): void {

    const emp: Employee = this.employees.find(item => item.id === id);

    const employee = cloneDeep(emp);

    this.inputFormValidators(id);

    delete employee.password;

    this.editMap.set(id, employee);

    emp.edit = true;

    this.inputFormValidators(id);
  }

  cancelEdit(id: number): void {

    const index: number = this.employees.findIndex(item => item.id === id);
    this.employees[index].edit = false;
  }


  create(id: any) {

    const employeeCopy = cloneDeep(this.editMap.get(id));

    delete employeeCopy.edit;

    this.postEmployee(employeeCopy);

    this.addEmployeRecord = false;
  }


  update(idEmp: number) {

    const employeeCp = cloneDeep(this.editMap.get(idEmp));

    // delete extra for put request
    delete employeeCp.edit;

    this.putEmployee(employeeCp);
  }


  async cancel() {
    const ref: NzModalRef = this.modal.confirm({
      nzTitle: 'Are you sure you want to cancel adding new customer?',
      nzOnOk: () => this.popConfirm.popUpDialog(true),
      nzOnCancel: () => this.popConfirm.popUpDialog(false)
    });

    this.handleOkDrawer = await this.popConfirm.confirm.pipe(take(1)).toPromise();
    if (this.handleOkDrawer) {
      this.closeDrawer();
    }
  }

  closeDrawer(): void {
    this.visibleDrawer = false;
    this.addEmployeRecord = false;
  }

  logout() {
    this.logoutService.logout();
  }

   // setters for temporary  input  edit fields
  setMapUsername(id: number, value: any) {
    const emp: Employee = this.editMap.get(id);
    emp.username = value;

    this.editMap.set(id, emp);
  }

  setMapPassword(id: number, value: any) {
    const emp: Employee = this.editMap.get(id);
    emp.password = value;
    this.editMap.set(id, emp);
  }

  setMapFirstName(id: number, value: any) {
    const emp: Employee = this.editMap.get(id);
    emp.firstName = value;
    this.editMap.set(id, emp);

  }
  setMapLastName(id: number, value: any) {
    const emp: Employee = this.editMap.get(id);
    emp.lastName = value;
    this.editMap.set(id, emp);
  }


 openModal(id:number){
  this.iSPasswordModalVisible=true;
  this.updatedPasswordId=id;
  const emp: Employee = {
    id: this.updatedPasswordId,
    username: this.editMap.get(id).username,
    password: '',
    firstName: this.editMap.get(id).firstName,
    lastName: this.editMap.get(id).lastName
  };

  this.editMap.set(id,emp);
 }

 closeModal(){
  this.iSPasswordModalVisible=false;
 }


  openDrawer(): void {
    this.visibleDrawer = true;
  }

  startAdding(): void {

    this.addEmployeeId = 0;

    const emp: Employee = {
      username: '',
      password: '',
      firstName: '',
      lastName: '',
    };

    const empCopy = cloneDeep(emp);
    this.editMap.set(this.addEmployeeId, empCopy);

    this.addEmployeRecord = true;

    this.inputFormValidators(this.addEmployeeId);
    this.openDrawer();
  }


  putEmployee(emp: Employee) {
    this.httpService.putRequestEmployee(emp)
      .subscribe(
        res => console.log(res),
        error => {
          console.log(error);

          this.getPageData(this.pageIndex);

          this.messageResult.success('error updating employee');
        },
        () => {
          this.getPageData(this.pageIndex);
          this.messageResult.success('success updating employee');
          this.iSPasswordModalVisible=false;
        });

  }

  postEmployee(emp: Employee) {
    const firstPage = 1;
    this.httpService.postRequestEmployee(emp)
      .subscribe(res => { console.log(res); },
        error => {
          console.log(error);
          this.messageResult.error('something went wrong saving employee');
        },
        () => {
          this.messageResult.success('success adding employee');
          this.getPageData(firstPage);
        });
  }


  deleteEmployee(id: number) {
    this.httpService.deleteRequestEmployee(id).pipe(
      tap(() => {
        const lastPage = Math.ceil((this.employees.length / this.pageSize));
        const lastPageCompare  = Math.ceil(((this.employees.length - 1) / this.pageSize));

     /**  If page has one element  pageIndex gets correct value
          for  getPageData(this.pageIndex)
      */
        if ((lastPage - lastPageCompare ) === 1) {
          this.pageIndex = this.pageIndex - 1;
        }

      }))
      .subscribe(
        res => console.log(res),
        error => {
          console.log(error);
          this.messageResult.error('something went wrong deleting employee');
        },
        () => {
          this.messageResult.success('success deleting employee');
          this.getPageData(this.pageIndex);
        });
  }


}
