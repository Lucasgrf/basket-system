import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../Types/login-response-type';
import { environment } from '../environment';

const BASE_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  signup(username: string,email: string, password: string, role: string): Observable<any> {
    return this.http.post<LoginResponse>(BASE_URL + "/auth/register", {username,email,password,role}).pipe(
      tap(
        (value) => {
            const userId = value['id'];
            const jwtToken = value['token'];
            const role = value['role'];
            localStorage.setItem('userId', userId.toString());
            localStorage.setItem('role', role);
            localStorage.setItem('JWT', jwtToken);
        }
      )
    )
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post<LoginResponse>(BASE_URL + "/auth/login", {email,password}).pipe(
      tap(
        (value) => {
            const jwtToken = value['token'];
            const role = value['role'];
            const userId = value['id'];
            localStorage.setItem('role', role);
            localStorage.setItem('JWT', jwtToken);
            localStorage.setItem('userId', userId.toString());
        }
      )
    )
  }


  isLogged() {
    const jwtToken = localStorage.getItem('JWT');
    if (jwtToken) {
      return true;
    }
    return false;
  }

  isAdmin() {
    const role = localStorage.getItem('role');
    if (role === 'ADMIN') {
      return true;
    }
    return false;
  }

}
