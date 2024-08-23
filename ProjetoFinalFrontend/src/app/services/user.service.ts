import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environment';
import { User } from '../models/user';
import { RequestConfirm } from '../models/requestConfirm';
import { UserResponse } from '../Types/user-response-type';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/users`;  // Ajuste o URL base da API conforme necessário

  user!: User;
  constructor(private http: HttpClient) { }

  // Método para obter um usuário pelo ID
  getUserById(id: number): Observable<any> {
    return this.http.get<UserResponse>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para atualizar o perfil do usuário
  updateProfile(id: number, body: RequestConfirm): Observable<any> {
    return this.http.put<UserResponse>(`${this.apiUrl}/${id}`, body).pipe(
      catchError(this.handleError)
    );
  }

  // Método para excluir o perfil do usuário
  deleteProfile(userId: number, body: RequestConfirm): Observable<void> {
    return this.http.request<void>('DELETE', `${this.apiUrl}/${userId}`, {
      body: body
    }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}
