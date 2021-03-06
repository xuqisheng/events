import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgaModule } from '../../../theme/nga.module';

import { routing }       from './login.routing';

import { RouteService } from '../../../service/route';
import { RequestService } from '../../../service/request';
import { UserService } from '../../../service/user';

import { Login } from './login.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgaModule,
    routing
  ],
  declarations: [
    Login
  ],
  providers: [
    RouteService,
    RequestService,
    UserService
  ]
})
export default class LoginModule {}
