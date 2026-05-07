import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environment';
import { Team } from '../models/team.model';
import { Player } from '../models/player.model';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiUrl = `${environment.apiUrl}/team`;  // URL base da API para equipes

  constructor(private http: HttpClient) { }

  // Método para obter todos os jogadores de um time pelo ID do time
  getAllPlayersTeam(teamId: number): Observable<Set<any>> {
    return this.http.get<Set<Player>>(`${this.apiUrl}/${teamId}/players`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para obter uma equipe pelo ID
  getTeamById(id: number): Observable<any> {
    return this.http.get<Team>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para obter todas as equipes (ADMIN)
  getAllTeams(): Observable<any[]> {
    return this.http.get<Team[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para excluir uma equipe pelo ID (ADMIN)
  deleteTeam(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para adicionar uma nova equipe (ADMIN)
  addTeam(team: Team): Observable<any> {
    return this.http.post<Team>(`${this.apiUrl}/add`, team).pipe(
      catchError(this.handleError)
    );
  }

  // Método para atualizar uma equipe pelo ID
  updateTeam(id: number, team: Team): Observable<any> {
    return this.http.put<Team>(`${this.apiUrl}/${id}`, team).pipe(
      catchError(this.handleError)
    );
  }

  // Método para tratar erros
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}
