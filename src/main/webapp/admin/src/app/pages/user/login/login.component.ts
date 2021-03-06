import {Component, ViewEncapsulation} from '@angular/core';
import {FormGroup, AbstractControl, FormBuilder, Validators} from '@angular/forms';

import { RouteService } from '../../../service/route';
import { UserService } from '../../../service/user';

@Component({
  selector: 'login',
  encapsulation: ViewEncapsulation.None,
  styles: [require('./login.scss')],
  template: require('./login.html'),
})
export class Login {

  public form:FormGroup;
  public email:AbstractControl;
  public password:AbstractControl;
  public rememberMe:AbstractControl;

  public submitted:boolean = false;
  public errors: string;

  constructor(fb:FormBuilder, private userService: UserService, private routeService: RouteService) {
    this.form = fb.group({
      'email': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'rememberMe': [true, null]
    });

    this.email = this.form.controls['email'];
    this.password = this.form.controls['password'];
    this.rememberMe = this.form.controls['rememberMe'];
  }

  public onSubmit(values:Object):void {
    let that = this;
    that.submitted = true;

    this.userService.login(values['email'], values['password'], values['rememberMe']).subscribe((err:any) => {
      that.errors = err;
    });
  }
}
