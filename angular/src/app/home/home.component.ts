import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SaveUserAuthService } from '../services/save-user-auth.service';
import * as isUndefined from 'lodash/isUndefined';
import { HttpRequestsService } from '../services/http-requests.service';

@Component({
    selector: 'home',
    template: ' ',
})

export class HomeComponent implements OnInit {

    userName: string;
    token: any;

    constructor(private router: Router,
                private state: SaveUserAuthService,
                private httpService: HttpRequestsService
        ) { }

    ngOnInit() {
        this.token = localStorage.getItem('bearerToken');
        console.log('token is'   + this.token);

        if ( !(isUndefined(this.token) )){

         this.httpService.getUserInfo().subscribe(data => {
             this.state.userAuth = data.role;


          },
          error => {
              console.log(error);
              this.router.navigate(['login']);


          },
          () => {
              this.router.navigate(['customers']);

          }
          );


        }
        else{
            this.router.navigate(['login']);
         }
    }



}
