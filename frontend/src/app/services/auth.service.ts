import { Injectable } from '@angular/core';
import { Login } from '../models/login';
import { HttpClient } from '@angular/common/http';
import { API_CONFIG } from '../config/api.config';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  authenticate(creds: Login): Observable<any> {
    return this.http.post(`${API_CONFIG.baseUrl}/login`, creds, {
      observe: 'response',
      responseType: 'text'
    });
  }

  successfullLogin(authToken: string) {
    localStorage.setItem('token', authToken);
    //console.log('Token armazenado no localStorage:', localStorage.getItem('token'));
  }

  isAuthenticated(): boolean {
    try {
      if (typeof localStorage !== 'undefined') {
        const token = localStorage.getItem('token');
        if (token) {
          return !this.jwtService.isTokenExpired(token);
        }
      }
      return false;
    } catch (error) {
      //console.error('Erro ao acessar localStorage:', error);
      return false;
    }
  }

  logout() {
    localStorage.clear();
  }
}
