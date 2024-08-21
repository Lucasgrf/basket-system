import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'https://sua-api.com/api/auth'; // Substitua pela URL da sua API

  constructor(private client: HttpClient) { }

  register(userData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.client.post<any>(this.apiUrl+"/register", userData, { headers });
  }
}
