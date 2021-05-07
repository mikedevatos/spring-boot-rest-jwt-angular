import { Injectable } from '@angular/core';
import { NzModalService } from 'ng-zorro-antd/modal';

@Injectable({
  providedIn: 'root'
})
export class MessageResultService {

  constructor(    private modal: NzModalService) { }


  success(message: string): void {
    const modal = this.modal.success({
      nzTitle: 'This is a notification message',
      nzContent: message
    });
    setTimeout(() => modal.destroy(), 3000);
  }


  error( errorContent: string): void {
    const modal =  this.modal.error({
       nzTitle: 'This is an error message',
       nzContent: errorContent
     });

    setTimeout(() => modal.destroy(), 3000);

   }




}
