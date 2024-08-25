import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../environment';
import { Credential } from '../models/credential.model';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class CredentialService {
  private apiUrl = `${environment.apiUrl}/credential`; // URL base para o serviço

  constructor(private http: HttpClient) { }

  // Método para atualizar um Credential
  update(id: number, credential: Credential): Observable<any> {
    return this.http.put<Credential>(`${this.apiUrl}/${id}`, credential).pipe(
      catchError(this.handleError)
    );
  }

  // Método para excluir um Credential
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getCredentialById(id: number): Observable<any> {
    return this.http.get<Credential>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para obter todas as Credentials
  getAll(): Observable<any[]> {
    return this.http.get<Credential[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para tratar erros
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}

