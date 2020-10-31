import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SaveUserAuthService } from './save-user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  constructor(private route: Router, private stateService: SaveUserAuthService) { }

  logout() {
    localStorage.setItem('bearerToken', '');
    this.route.navigate(['login']);
   }

}
