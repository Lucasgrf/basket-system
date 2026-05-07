import { Component, OnInit } from '@angular/core';
import { Team } from '../../../models/team.model';
import { TeamService } from '../../../services/team.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from "../header/header.component";
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [CommonModule, RouterLink, HeaderComponent, ConfirmationDialogComponent],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.scss'
})
export class TeamsComponent implements OnInit {
  teams: Team[] = [];
  selectedTeamId: number | null = null;
  showModal: boolean = false;

  constructor(private teamService: TeamService) {}

  ngOnInit() {
    this.loadTeams();
  }

  loadTeams() {
    this.teamService.getAllTeams().subscribe(teams => {
      this.teams = teams;
    });
  }

  openDeleteModal(teamId: number): void {
    this.selectedTeamId = teamId;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.selectedTeamId = null;
    this.showModal = false;
  }

  confirmDelete(): void {
    if (this.selectedTeamId !== null) {
      this.teamService.deleteTeam(this.selectedTeamId).subscribe({
        next: () => {
          this.loadTeams(); // Recarregar a lista de times após a exclusão
          this.closeDeleteModal();
        },
        error: (error) => {
          console.error('Erro ao excluir o time', error);
          this.closeDeleteModal();
        }
      });
    }
  }

}
