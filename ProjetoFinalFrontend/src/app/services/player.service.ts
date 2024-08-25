import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../environment';
import { Player } from '../models/player.model';
import { Team } from '../models/team.model';
import { Training } from '../models/training.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private apiUrl = `${environment.apiUrl}/player`;  // URL base da API para jogadores

  constructor(private http: HttpClient, private auth: AuthService) { }

  deletePlayer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Método para associar um jogador a um time
  joinTeam(player: Player): Observable<any> {
    return this.http.post(`${this.apiUrl}/join`, player).pipe(
      catchError(this.handleError)
    );
  }

  // Método para desvincular um jogador de um time
  leaveTeam(player: Player): Observable<any> {
    return this.http.delete(`${this.apiUrl}/leave`, { body: player }).pipe(
      catchError(this.handleError)
    );
  }

  // Método para confirmar presença em um treino
  confirmPresenceTraining(player: Player, trainingId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/confirm/${trainingId}`, player).pipe(
      catchError(this.handleError)
    );
  }

  // Método para registrar ausência em um treino
  absenceTraining(player: Player, trainingId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/absence/${trainingId}`, player).pipe(
      catchError(this.handleError)
    );
  }

// Método para visualizar o time ao qual o jogador pertence
viewTeam(playerId: number): Observable<any> {
  return this.http.get<Team>(`${this.apiUrl}/team/${playerId}`).pipe(
    catchError(this.handleError)
  );
}

// Método para visualizar a credencial do jogador
viewCredential(playerId: number): Observable<any> {
  return this.http.get<Credential>(`${this.apiUrl}/credential/${playerId}`).pipe(
    catchError(this.handleError)
  );
}

// Método para visualizar os treinos do jogador
viewTrainings(playerId: number): Observable<any[]> {
  return this.http.get<Training[]>(`${this.apiUrl}/trainings/${playerId}`).pipe(
    catchError(this.handleError)
  );
}

  // Método para obter todos os jogadores (ADMIN)
  getAllPlayers(): Observable<any[]> {
    return this.http.get<Player[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  // Método para atualizar as informações do jogador
  updatePlayer(player: Player, id: number): Observable<any> {
    return this.http.put<Player>(`${this.apiUrl}/${id}`, player).pipe(
      catchError(this.handleError)
    );
  }

  // Método para tratar erros
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error));
  }
}
