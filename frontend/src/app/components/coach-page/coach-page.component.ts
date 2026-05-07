import { Component, OnInit } from '@angular/core';
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from "../menu/menu.component";
import { HeaderComponent } from "../header/header.component";
import { Player } from '../../models/player.model';
import { CommonModule } from '@angular/common';
import { Coach } from '../../models/coach.model';
import { Team } from '../../models/team.model';
import { CoachService } from '../../services/coach.service';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-coach-page',
  standalone: true,
  imports: [CommonModule,MenuLateralComponent, FooterComponent, MenuComponent, HeaderComponent],
  templateUrl: './coach-page.component.html',
  styleUrl: './coach-page.component.scss'
})
export class CoachPageComponent implements OnInit {
  coaches: Coach[] = [];

  teams: Team[] = [];

  photo: string = 'https://static.vecteezy.com/system/resources/previews/018/765/757/original/user-profile-icon-in-flat-style-member-avatar-illustration-on-isolated-background-human-permission-sign-business-concept-vector.jpg';

  constructor(private coachService: CoachService, private teamService: TeamService) { }

  ngOnInit(): void {
    this.loadCoachesAndTeams();
  }

  loadCoachesAndTeams(): void {
    this.coachService.getAllCoaches().subscribe({
      next: (coaches: Coach[]) => {
        this.coaches = coaches;
      },
      error: (err) => console.error('Erro ao carregar treinadores:', err)
    });
    this.teamService.getAllTeams().subscribe({
      next: (teams: Team[]) => {
        this.teams = teams;
      },
      error: (err) => console.error('Erro ao carregar times:', err)
    });
  }

  getTeamName(teamId?: number): string {
    const team = this.teams.find(t => t.id === teamId);
    return team ? team.name : 'Sem Time';
  }

  verDetalhes(coach: Coach): void {
    // Lógica para abrir modal de detalhes do treinador
    console.log(coach);
  }

  enviarMensagem(coach: Coach): void {
    // Lógica para enviar mensagem ao treinador
    console.log(`Enviando mensagem para ${coach.nickname}`);
  }
}
