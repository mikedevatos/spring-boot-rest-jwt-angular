import { Injectable } from '@angular/core';
import { RoomsModel } from '../model/rooms-model';
import { Observable } from 'rxjs';
import { HttpClient} from '@angular/common/http';
import { CustomerModel } from '../model/customer-model';
import { CustomersDTO } from '../model/customers-dto';
import { EmployeeDTO } from '../model/employee-dto';
import { EmployeeModel } from '../model/employee-model';
import { CurrentUser } from '../model/current-user';
import { tap } from 'rxjs/operators';
import { SaveUserAuthService } from './save-user-auth.service';


@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {
  urlRoom: string;
  urlCusto: string;
  urlEmployee: string;
  userInfoUrl: string;
  loginUrl: string;


  constructor(private http: HttpClient,
              private state: SaveUserAuthService,
    ) {

    this.urlRoom = 'http://localhost:8888/api/room';
    this.urlEmployee = 'http://localhost:8888/api/employee';
    this.urlCusto =  'http://localhost:8888/api/customer';
    this.userInfoUrl = 'http://localhost:8888/api/userinfo';
    this.loginUrl =  'http://localhost:8888/api/login';


  }


  getUserInfoPromise(){

 const promise = new Promise((resolve, reject) => {
     this.getUserInfo().toPromise()
      .then( result => {


        this.state.userAuth = result.role;


        resolve();


    },
   err =>  {
      console.log(err);
      reject();
      return;
   }   ); }  );

 return promise;

    }




  login(userName: any, pass: any){
  return  this.http.post<Observable<any>>(this.loginUrl, {

       username: userName,
        password: pass}

    , {observe: 'response'}).pipe(
      tap(resp => {

        const bearerToken = resp.headers.get('Authorization');
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          localStorage.setItem('bearerToken', jwt);

        }

      }));

  }


  getUserInfo(){


    return this.http.get<CurrentUser>(this.userInfoUrl);

  }






  postReqCustomer(customer: CustomerModel): Observable<any> {



   return this.http.post<CustomerModel>(this.urlCusto, customer, { observe: 'response' });

  }


  putReqCustomer(customer: CustomerModel): Observable<any>{

   return this.http.put<CustomerModel>(this.urlCusto, customer, { observe: 'response' });

  }


  getCustomers(pageIndex: number, pageSize: number): Observable<CustomersDTO>{

    return  this.http.get<CustomersDTO>(this.urlCusto + '/' + pageIndex  + '/' + pageSize);

  }



  deleteReqCustomer(id: number): Observable<any> {

    return  this.http.delete<any>(this.urlCusto + '/' + id);

   }




   getRequestRoom(): Observable<RoomsModel[]>{

    return  this.http.get<RoomsModel[]>(this.urlRoom);
  }




  deleleReqRoom(idCustomer: number, idRoom: number): Observable<any>{

  return this.http.delete<any>(this.urlRoom + '/' + idCustomer + '/' + idRoom);


  }







  getEmployees(pageIndex: number, pageSize: number): Observable<EmployeeDTO>{

    return  this.http.get<EmployeeDTO>(this.urlEmployee + '/' + pageIndex  + '/' + pageSize);

  }


  putReqEmployee(employee: EmployeeModel): Observable<any>{

   return this.http.put<EmployeeModel>(this.urlEmployee, employee, { observe: 'response' });

  }




  postReqEmployee(employee: EmployeeModel): Observable<any> {


    return this.http.post<EmployeeModel>(this.urlEmployee, employee, { observe: 'response' });

   }





  deleteReqEmployee(id: number): Observable<any> {

    return  this.http.delete<any>(this.urlEmployee + '/' + id);

   }
}
