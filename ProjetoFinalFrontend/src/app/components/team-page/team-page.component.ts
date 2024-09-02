import { Component, OnInit } from '@angular/core';
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from "../menu/menu.component";
import { HeaderComponent } from "../header/header.component";
import { CommonModule } from '@angular/common';
import { Team } from '../../models/team.model';
import { Router } from '@angular/router';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-team-page',
  standalone: true,
  imports: [CommonModule,MenuLateralComponent, FooterComponent, MenuComponent, HeaderComponent],
  templateUrl: './team-page.component.html',
  styleUrl: './team-page.component.scss'
})
export class TeamPageComponent implements OnInit {
  teams: Team[] = [];
  userRole: string | null = null; // Armazenar a role do usuário

  photo: string = 'https://image.freepik.com/vetores-gratis/logotipo-do-basquetebol_20448-18.jpg';

  constructor(private teamService: TeamService) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role'); // Obter a role do localStorage
    this.loadTeams();
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe((teams: Team[]) => {
      this.teams = teams;
    });
  }

  // Método para o jogador pedir para se juntar ao time
  requestToJoinTeam(teamId: number): void {
    console.log(`Pedido enviado para se juntar ao time ${teamId}`);
    // Implementar a lógica de enviar a requisição ao backend
  }

  // Método para o treinador pedir para ser treinador do time
  requestToCoachTeam(teamId: number): void {
    console.log(`Pedido enviado para ser treinador do time ${teamId}`);
    // Implementar a lógica de enviar a requisição ao backend
  }
}
