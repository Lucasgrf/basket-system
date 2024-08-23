import { Component, OnInit } from '@angular/core';
import { Player } from '../../../models/player.model';
import { PlayerService } from '../../../services/player.service';
import { MatDialog } from '@angular/material/dialog';
import { PlayerDialogComponent } from '../../player-dialog/player-dialog.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-players',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './players.component.html',
  styleUrl: './players.component.scss'
})
export class PlayersComponent implements OnInit {
  players: Player[] = [];

  constructor(private playerService: PlayerService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadPlayers();
  }

  loadPlayers() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(PlayerDialogComponent, {
      width: '600px',
      data: { action: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPlayers();
      }
    });
  }

  openEditDialog(player: Player) {
    const dialogRef = this.dialog.open(PlayerDialogComponent, {
      width: '600px',
      data: { action: 'edit', player }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPlayers();
      }
    });
  }

  deletePlayer(id: number) {

  }
}
