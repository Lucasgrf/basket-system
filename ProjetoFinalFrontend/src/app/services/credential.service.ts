import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { Credential } from '../models/credential.model';

@Injectable({
  providedIn: 'root'
})
export class CredentialService {

  private apiUrl = `${environment.apiUrl}`; // URL base para o serviço

  constructor(private http: HttpClient) { }

  // Método para criar um Credential
  create(userId: number): Observable<any> {
    return this.http.post<Credential>(`${this.apiUrl}`, { userId });
  }

  // Método para atualizar um Credential
  update(id: number, credential: Credential): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, credential);
  }

  // Método para excluir um Credential
  delete(id: number, confirmation: string): Observable<void> {
    const params = new HttpParams().set('confirmation', confirmation);
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { params });
  }
}
