import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { Coach } from '../models/coach.model';

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  private apiUrl = `${environment.apiUrl}/coaches`; // URL base para o serviço

  constructor(private http: HttpClient) { }

  // Método para criar um Coach
  createCoach(coach: Coach): Observable<any> {
    return this.http.post<Coach>(`${this.apiUrl}/add`, coach);
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
  updateCoach(id: number, coach: Coach): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/update/${id}`, coach);
  }

  // Método para excluir um Coach
  deleteCoach(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
