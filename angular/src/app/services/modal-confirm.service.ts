import { Injectable } from '@angular/core';
import {  Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class popConfirmHandleCancelService {

  public confirmSource = new Subject<boolean>();
  confirm = this.confirmSource.asObservable(); 

  popUpDialog(data: boolean){
   this.confirmSource.next(data);
  }

  constructor() { }
}
