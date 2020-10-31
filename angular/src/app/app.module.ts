import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {  HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { CustomersComponent } from './customers/customers.component';
import { HomeComponent } from './home/home.component';
import {  HttpRequestInterceptor } from './http-interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientJsonpModule } from '@angular/common/http';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { EmployeeComponent } from './employee/employee.component';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzMenuModule}  from 'ng-zorro-antd/menu';
import {NzPopconfirmModule}  from 'ng-zorro-antd/popconfirm';
import {NzModalModule}  from 'ng-zorro-antd/modal';
import {NzMessageModule} from 'ng-zorro-antd/message';
import {NzButtonModule}  from 'ng-zorro-antd/button';
import {NzPaginationModule} from 'ng-zorro-antd/pagination';
import {NzCollapseModule}  from 'ng-zorro-antd/collapse';
import {NzIconModule}  from 'ng-zorro-antd/icon';
import {NzDropDownModule}  from 'ng-zorro-antd/dropdown';
import {NzFormModule}  from   'ng-zorro-antd/form';
 import {NzDrawerModule}  from  'ng-zorro-antd/drawer';
 import {NzPageHeaderModule}  from 'ng-zorro-antd/page-header'   ;       
 import {NzLayoutModule}  from       'ng-zorro-antd/layout';
 import {NzSpinModule}  from      'ng-zorro-antd/spin';
 import {NzDividerModule}  from 'ng-zorro-antd/divider';
 import {NzToolTipModule}  from 'ng-zorro-antd/tooltip';
 import {en_US, NZ_I18N}  from  'ng-zorro-antd/i18n';

import {NzSelectModule}  from   'ng-zorro-antd/select';
import {NzAlertModule}  from   'ng-zorro-antd/alert';





registerLocaleData(en);
   

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CustomersComponent,
    HomeComponent,
    EmployeeComponent
    
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
     HttpClientJsonpModule, 
     ReactiveFormsModule, 
     NzTableModule, 
     BrowserAnimationsModule,
     NzInputModule,
     NzMenuModule,
     NzPopconfirmModule,
     NzModalModule,
     NzMessageModule,
     NzButtonModule,
     NzPaginationModule,
     NzCollapseModule,
     NzIconModule,
     NzDropDownModule,
     NzFormModule,
    NzAlertModule,
     NzDrawerModule,
     NzPageHeaderModule,
     NzLayoutModule,
     NzSelectModule,
     NzSpinModule,
     NzDividerModule,
     NzToolTipModule

    
    
  ],
  
  providers: [{
     provide: HTTP_INTERCEPTORS, 
    useClass: HttpRequestInterceptor, 
    multi: true }, { provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})

export class AppModule { }
