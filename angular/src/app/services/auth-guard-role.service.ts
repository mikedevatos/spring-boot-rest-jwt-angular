import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { SaveUserAuthService } from './save-user-auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import * as isUndefined from 'lodash/isUndefined';
import { HttpRequestsService } from './http-requests.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuardRoleService implements CanActivate {

  currentUser: any;

  constructor(private router: Router,
              private authStateService: SaveUserAuthService,
              private httpService: HttpRequestsService
    ) { }


  async canActivate(): Promise<boolean | UrlTree> {
    const employee = 'EMPLOYEE';
    const manager = 'MANAGER';

    if (isUndefined(this.authStateService.userAuth)) {



      try {

        await (await this.httpService.getUserInfoPromise().catch(err => { console.log(err); }));
      }
      catch (e) {
        console.log(e instanceof HttpErrorResponse);
        throw e;
      }




      if (this.authStateService.userAuth.valueOf() === manager) {

        return true;
      }
      else if (this.authStateService.userAuth.valueOf() === employee) {


        return false;
      }
    }


    if (this.authStateService.userAuth.valueOf() === manager) {

      return true;
    }
    else if (this.authStateService.userAuth.valueOf() === employee) {


      return false;
    }



    return this.router.createUrlTree(['/login']);

  }
}
