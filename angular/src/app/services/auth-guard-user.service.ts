import { Injectable } from '@angular/core';
import {  } from '../model/current-user';
import * as isUndefined from 'lodash/isUndefined';



import {
  CanActivate,
  Router,
  UrlTree,
} from '@angular/router';
import { SaveUserAuthService } from './save-user-auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { HttpRequestsService } from './http-requests.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuardUserService implements CanActivate {


 Userrole: any;


  constructor(private router: Router
    ,         private authStateService: SaveUserAuthService
    ,         private httpService: HttpRequestsService) { }


 async canActivate(): Promise<boolean | UrlTree>{
  const employee = 'EMPLOYEE';
  const manager = 'MANAGER';

  if (!isUndefined(this.authStateService.userAuth)){

          this.Userrole = this.authStateService.userAuth;

       }
       else if (isUndefined(this.Userrole)){



        try {

          await  (await this.httpService.getUserInfo().pipe(

               tap(
                 data =>  {

                this.authStateService.userAuth = data.role;
               },

               err => {
                 console.log(err);
               }

               )

          )
          .toPromise()


          .catch(err => {console.log(err); }));
        } catch (e) {
          console.log(e instanceof HttpErrorResponse); // true
          throw e;
        }



        this.Userrole = this.authStateService.userAuth;


       }
  if ((this.Userrole === manager) || (this.Userrole === employee)){
         return true;
       }

  return this.router.createUrlTree(['/login']);

     }


    }

