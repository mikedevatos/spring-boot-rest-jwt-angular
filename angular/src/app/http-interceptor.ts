import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    var token = localStorage.getItem('bearerToken');
    console.log("token taken "+token);

    if(token) {
      const newReq = req.clone(
        { 
           headers: req.headers.set('Authorization',
                    'Bearer ' + token)
        });

        return next.handle(newReq);
    }
    else {
     return  next.handle(req);
    }
  }
};


export class HttpInterceptorModule { }