import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { SaveUserAuthService } from './save-user-auth.service';
import * as isUndefined from 'lodash/isUndefined';
import { HttpRequestsService } from './http-requests.service';
import { tap } from 'rxjs/operators';


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

        await this.httpService.getUserInfo()
                                      .pipe(
                                           tap(
                                                data =>  {this.authStateService.userAuth = data.role; },
                                                err => {console.log(err)},
                                                () => {}
                                              )
                                      )
                                     .toPromise()
                                     .catch(err=> { console.log(err); } )


    }


    if (this.authStateService.userAuth.valueOf() === manager)
    return true;

    return this.router.createUrlTree(['/login']);

  }
}
