import { PersonDTO } from './../model/person-dto';
import { Injectable } from '@angular/core';
import { Room } from '../model/room';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Customer} from '../model/customer';
import { CustomersDTO } from '../model/customers-dto';
import { RoomsDTO } from '../model/rooms-dto';
import { EmployeeDTO } from '../model/employee-dto';
import { Employee } from '../model/employee';
import { CurrentUser } from '../model/current-user';
import { tap } from 'rxjs/operators';
import { Person } from '../model/person';
import { Booking } from '../model/booking';
import { CustomerDto } from "../model/customer-dto";


@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {
  urlRoom: string;
  urlCusto: string;
  urlEmployee: string;
  userInfoUrl: string;
  loginUrl: string;
  urlPerson: string;
  urlBooking: string;


  constructor(private http: HttpClient,
  ) {

    this.urlRoom = 'http://localhost:8088/api/room';
    this.urlEmployee = 'http://localhost:8088/api/employee';
    this.urlCusto = 'http://localhost:8088/api/customer';
    this.urlPerson = 'http://localhost:8088/api/person';
    this.userInfoUrl = 'http://localhost:8088/api/userinfo';
    this.loginUrl = 'http://localhost:8088/api/login';
    this.urlBooking = 'http://localhost:8088/api/booking';

  }



  login(userName: any, pass: any) {
    return this.http.post<Observable<any>>(this.loginUrl, {
      username: userName,
      password: pass
       }, { observe: 'response' }).pipe(

        tap(resp => {
          const bearerToken = resp.headers.get('Authorization');
          if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
            const jwt = bearerToken.slice(7, bearerToken.length);
            localStorage.setItem('bearerToken', jwt);
          }
        }));
  }


  getUserInfo() {

    return this.http.get<CurrentUser>(this.userInfoUrl);

  }

  getRequestEmployees(pageIndex: number, pageSize: number): Observable<EmployeeDTO> {

    return this.http.get<EmployeeDTO>(this.urlEmployee + '/' + pageIndex + '/' + pageSize);

  }


  getRequestCustomers(pageIndex: number, pageSize: number): Observable<CustomersDTO> {

    return this.http.get<CustomersDTO>(this.urlCusto + '/' + pageIndex + '/' + pageSize);

  }



  getPageRooms(pageIndex: number, pageSize: number): Observable<RoomsDTO> {

    return this.http.get<RoomsDTO>(this.urlRoom + '/' + pageIndex + '/' + pageSize);

  }


  getBookingsOfRoomById(idRoom: number): Observable<Booking[]> {

    return this.http.get<Booking[]>(this.urlRoom + '/' + idRoom);

  }


  getRequestRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(this.urlRoom);
  }


  postRequestCustomer(customer: Customer): Observable<any> {
    return this.http.post<Customer>(this.urlCusto, customer, { observe: 'response' });
  }

  postRequestPerson(personDto: PersonDTO): Observable<any> {
    return this.http.post<PersonDTO>(this.urlPerson, personDto, { observe: 'response' });
  }

  postRequestEmployee(employee: Employee): Observable<any> {
    return this.http.post<Employee>(this.urlEmployee, employee, { observe: 'response' });
  }


  postRequestRoom(room: Room): Observable<any> {
    return this.http.post<Room>(this.urlRoom, room, { observe: 'response' });
  }



  putRequestBooking(booking: Booking): Observable<any> {
    return this.http.put<Booking>(this.urlBooking, booking, { observe: 'response' });
  }


  putRequestDtoCustomer(customer: CustomerDto): Observable<any> {
    return this.http.put<CustomerDto>(this.urlCusto, customer, { observe: 'response' });
  }


  putRequestCustomer(customer: Customer): Observable<any> {
    return this.http.put<Customer>(this.urlCusto, customer, { observe: 'response' });
  }

  putRequestPerson(customer: Person): Observable<any> {
    return this.http.put<Person>(this.urlPerson, customer, { observe: 'response' });
  }


  putRequestRoom(room: Room): Observable<any> {
    return this.http.put<Room>(this.urlRoom, room, { observe: 'response' });
  }


  putRequestEmployee(employee: Employee): Observable<any> {
    return this.http.put<Employee>(this.urlEmployee, employee, { observe: 'response' });
  }



  deleteRequestCustomer(id: number): Observable<any> {
    return this.http.delete<any>(this.urlCusto + '/' + id);
  }


  deleteRequestPerson(id: number): Observable<any> {
    return this.http.delete<any>(this.urlPerson + '/' + id, { observe: 'response' });

  }

  deleteRequestEmployee(id: number): Observable<any> {
    return this.http.delete<any>(this.urlEmployee + '/' + id);
  }

  deleleRequestRoom(idRoom: number): Observable<any> {
    return this.http.delete<any>(this.urlRoom + '/' + idRoom);
  }

}
