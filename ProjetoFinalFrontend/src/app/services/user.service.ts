import { User } from './../models/user';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environment';
import { RequestConfirm } from '../models/requestConfirm';
import { UserResponse } from '../Types/user-response-type';
import { RequestUpdateUser } from '../models/requestUpdateUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/user`;  // Ajuste o URL base da API conforme necessário

  user!: User;
  constructor(private http: HttpClient) { }

  // Método para obter um usuário pelo ID
  getUserById(id: number): Observable<any> {
    return this.http.get<UserResponse>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para atualizar o perfil do usuário
  updateProfile(id: number, user: RequestUpdateUser): Observable<any> {
    return this.http.put<UserResponse>(`${this.apiUrl}/profile/${id}`, user).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(id: number, user: User): Observable<any> {
    return this.http.put<UserResponse>(`${this.apiUrl}/${id}`, user).pipe(
      catchError(this.handleError)
    );
  }

  createUser(user: User, password: string): Observable<UserResponse> {
    const params = new HttpParams().set('password', password);
    return this.http.post<UserResponse>(`${this.apiUrl}/add`, user, { params }).pipe(
      catchError(this.handleError)
    );
  }

  // Método para excluir o perfil do usuário
  deleteProfile(userId: number): Observable<void> {
    return this.http.request<void>('DELETE', `${this.apiUrl}/profile/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.request<void>('DELETE', `${this.apiUrl}/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}
