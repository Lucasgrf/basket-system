import { Injectable } from '@angular/core';
import { Training } from '../models/training.model';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {
  private apiUrl = `${environment.apiUrl}/training`;  // URL base da API para treinamentos

  constructor(private http: HttpClient) { }

  // Método para obter um treino pelo ID
  getTrainingById(id: number): Observable<any> {
    return this.http.get<Training>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para obter todos os treinamentos (ADMIN)
  getAllTrainings(): Observable<any[]> {
    return this.http.get<Training[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para excluir um treinamento pelo ID (ADMIN)
  deleteTraining(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para adicionar um novo treinamento (ADMIN)
  addTraining(training: Training): Observable<any> {
    return this.http.post<Training>(`${this.apiUrl}/add`, training).pipe(
      catchError(this.handleError)
    );
  }

  // Método para atualizar um treinamento pelo ID (ADMIN)
  updateTraining(id: number, training: Training): Observable<any> {
    return this.http.put<Training>(`${this.apiUrl}/update/${id}`, training).pipe(
      catchError(this.handleError)
    );
  }

  // Método para tratar erros
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}
