import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { User } from '../models/user';
import { environment } from '../environment';
import { Training } from '../models/training.model';
import { Team } from '../models/team.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) { }

  createUser(user: User, password: string): Observable<any> {
    return this.http.post<User>(`${this.apiUrl}/createUser`, { user, password }).pipe(
      catchError(this.handleError)
    );
  }

  getAllTrainings(): Observable<any[]> {
    return this.http.get<Training[]>(`${this.apiUrl}/trainings`).pipe(
      catchError(this.handleError)
    );
  }

  getAllTeams(): Observable<any[]> {
    return this.http.get<Team[]>(`${this.apiUrl}/teams`).pipe(
      catchError(this.handleError)
    );
  }

  getAllCredentials(): Observable<any[]> {
    return this.http.get<Credential[]>(`${this.apiUrl}/credentials`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para tratar erros
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}

