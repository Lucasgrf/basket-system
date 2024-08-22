import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../Types/login-response-type';

const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  signup(username: string,email: string, password: string, role: string): Observable<any> {
    return this.http.post<LoginResponse>(BASE_URL + "auth/register", {username,email,password,role}).pipe(
      tap(
        (value) => {
            const jwtToken = value['token'];
            const username = value['username'];
            const role = value['role'];
            localStorage.setItem('role', role);
            localStorage.setItem('JWT', jwtToken);
            localStorage.setItem('username', username);
        }
      )
    )
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post<LoginResponse>(BASE_URL + "auth/login", loginRequest).pipe(
      tap(
        (value) => {
            const jwtToken = value['token'];
            const username = value['username'];
            const role = value['role'];
            localStorage.setItem('role', role);
            localStorage.setItem('JWT', jwtToken);
            localStorage.setItem('username', username);
        }
      )
    )
  }

  private createAuthorizationHeader(): HttpHeaders {
    const jwtToken = localStorage.getItem('JWT');
    if (jwtToken) {
      return new HttpHeaders({
        'Authorization': 'Bearer ' + jwtToken
      });
    } else {
      console.log("JWT token not found in the Local Storage");
      return new HttpHeaders();
    }
  }

  private logout (){
    localStorage.removeItem('JWT');
  }

  get isLogged() {
    const jwtToken = localStorage.getItem('JWT');
    if (jwtToken) {
      return true;
    }
    return false;
  }

}
