import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { Coach } from '../models/coach.model';

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  private apiUrl = `${environment.apiUrl}/coach`; // URL base para o serviço

  constructor(private http: HttpClient) { }

  // Método para criar um Coach
  createCoach(coach: Coach): Observable<any> {
    return this.http.post<Coach>(`${this.apiUrl}`, coach);
  }

  // Método para obter um Coach por ID
  getCoach(id: number): Observable<any> {
    return this.http.get<Coach>(`${this.apiUrl}/${id}`);
  }

  // Método para obter todos os Coaches
  getAllCoaches(): Observable<any[]> {
    return this.http.get<Coach[]>(`${this.apiUrl}`);
  }

  // Método para atualizar um Coach
  updateCoach(id: number, coach: Coach): Observable<any> {
    return this.http.put<Coach>(`${this.apiUrl}/${id}`, coach);
  }

  // Método para excluir um Coach
  deleteCoach(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCoachByUserId(userId: number): Observable<any> {
    return this.http.get<Coach>(`${this.apiUrl}/user/${userId}`);
  }
}
