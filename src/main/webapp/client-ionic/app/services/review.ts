import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

import {PostService} from './post';

@Injectable()
export class ReviewService {
    static ENDPOINT: string = '/review/index';

    constructor(private _postService: PostService) {
    }

    getData(req): Observable<any> {
        return this._postService.post(ReviewService.ENDPOINT, req);
    }
}