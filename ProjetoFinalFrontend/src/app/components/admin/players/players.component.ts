import { Component, OnInit } from '@angular/core';
import { Player } from '../../../models/player.model';
import { PlayerService } from '../../../services/player.service';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";
import { RouterLink } from '@angular/router';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { UserService } from '../../../services/user.service';
import { TeamService } from '../../../services/team.service';

@Component({
  selector: 'app-players',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterLink, ConfirmationDialogComponent],
  templateUrl: './players.component.html',
  styleUrl: './players.component.scss'
})
export class PlayersComponent implements OnInit {
  players: Player[] = [];
  selectedPlayerId: number | null = null;
  showModal: boolean = false;
  userName: any;
  teamName: any;

  constructor(private playerService: PlayerService, private userService: UserService, private teamService: TeamService) {}

  ngOnInit() {
    this.loadPlayers();
  }

  loadPlayers() {
    this.playerService.getAllPlayers().subscribe(players => {
      this.players = players;
    });
  }

  openDeleteModal(playerId: number): void {
    this.selectedPlayerId = playerId;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.selectedPlayerId = null;
    this.showModal = false;
  }

  confirmDelete(): void {
    if (this.selectedPlayerId !== null) {
      this.playerService.deletePlayer(this.selectedPlayerId).subscribe({
        next: () => {
          this.loadPlayers(); // Recarrega a lista de jogadores após exclusão
          this.closeDeleteModal();
        },
        error: (error) => {
          console.error('Erro ao excluir o jogador', error);
          this.closeDeleteModal();
        }
      });
    }
  }
}
