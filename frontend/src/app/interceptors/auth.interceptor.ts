import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../services/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //debugger;
    //console.log('Interceptor - Intercepting request:', request);
    let token = localStorage.getItem('token');
    //console.log('Token obtido do localStorage:', token);
    
    if (token) {
      const cloneReq = request.clone({ 
        headers: request.headers.set('Authorization', `Bearer ${token}`)
      });
      //console.log('Requisição clonada com cabeçalho Authorization:', cloneReq);
      return next.handle(cloneReq);
    } else {
      //console.log('Requisição sem token');
      return next.handle(request);
    }
  }
}

export const AuthInterceptorProvider = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }
]
