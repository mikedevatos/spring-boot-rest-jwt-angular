import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CustomersComponent } from './customers/customers.component';
import { EmployeeComponent } from './employee/employee.component';
import { AuthGuardUserService } from './services/auth-guard-user.service';
import { AuthGuardRoleService } from './services/auth-guard-role.service';
import { RoomComponent } from './room/room.component';



const routes: Routes = [


{ path: '', component: HomeComponent},
{ path: 'login', component: LoginComponent },
{ path: 'customers', component: CustomersComponent, canActivate: [AuthGuardUserService] },
{ path: 'rooms', component: RoomComponent, canActivate: [AuthGuardUserService] },
{ path: 'employees', component: EmployeeComponent, canActivate: [AuthGuardRoleService]  },
{ path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
