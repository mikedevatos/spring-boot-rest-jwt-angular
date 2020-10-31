import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpRequestsService } from '../services/http-requests.service';

@Component({
  selector: 'app-customers',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: any;
  password: any;
  validateForm: FormGroup;
  errorMessage: boolean;



  constructor(
              private router: Router,
              private fb: FormBuilder,
              private httpService: HttpRequestsService
              )
              {}


  ngOnInit(): void {

    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
    this.errorMessage = false;
  }

  login() {

    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.username = this.validateForm.get('username').value;
    this.password = this.validateForm.get('password').value;

    this.httpService.login(this.username, this.password)

    .subscribe(res => {
      console.log('response is' + res.status);

    },
    err => {
      console.log(err);
      this.errorMessage = true;
      localStorage.setItem('bearerToken', '');

    }
    ,

    () => {

      this.router.navigate(['']);

    }

    );


}

}
