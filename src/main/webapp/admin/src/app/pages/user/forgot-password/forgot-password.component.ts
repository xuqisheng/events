import { Component,ViewEncapsulation, Pipe, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { DropdownModule} from 'ng2-bootstrap/ng2-bootstrap';

import { CONSTANT } from '../../../utils/constant';
import { Utils } from '../../../utils/utils';
import {ValidatorUtils} from '../../../validator/validator.utils';
import {EqualPasswordsValidator} from '../../../validator';

import { RouteService } from '../../../service/route';

import { UserService } from '../../../service/user';

declare var jQuery;

@Component({
  selector: 'forgot-password',
  encapsulation: ViewEncapsulation.None,
  styles: [require('./forgot-password.scss')],

  template: require('./forgot-password.html')
})
export class ForgotPassword implements OnInit, AfterViewInit {
  model: any = {};
  form: any;
  public errors: string;

  constructor(private _routeService: RouteService, private _route: ActivatedRoute, private fb: FormBuilder,
              private _userService: UserService) {

    let that = this;
    that.model['vcode'] = Utils.getUrlParam('vcode');
  }

  ngOnInit() {
    let that = this;

    that.buildForm();
    that.loadData();
  }

  ngAfterViewInit() {
    let that = this;

  }

  onSubmit():void {
    let that = this;

    that._userService.resetPassword(that.model).subscribe((err:any) => {
      that.formErrors = [err];
    });
  }

  loadData() {
    let that = this;

  }

  buildForm(): void {
    let that = this;
    this.form = this.fb.group(
      {
        'vcode': [that.model['vcode']],
        'password': [that.model['password'], [Validators.required, Validators.pattern(/^[0-9a-zA-Z]{6,10}$/)]],
        'rePassword': [that.model['rePassword'], [Validators.required, Validators.pattern(/^[0-9a-zA-Z]{6,10}$/)]],
      }, {validator: EqualPasswordsValidator.validate('password', 'rePassword')}
    );

    this.form.valueChanges.subscribe(data => this.onValueChanged(data));
    this.onValueChanged();
  }
  onValueChanged(data?: any) {
    let that = this;
    that.formErrors = ValidatorUtils.genMsg(that.form, that.validateMsg, ['passwordsEqual']);
  }

  formErrors = [];
  validateMsg = {
    'password': {
      'required':      '密码不能为空',
      'pattern': '密码必须为6到10位的字母和数字组合'
    },
    'rePassword': {
      'required':      '重复密码不能为空',
      'pattern': '重复密码必须为6到10位的字母和数字组合'
    },
    'passwordsEqual': '两次密码必须相同'
  };

}
