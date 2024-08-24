import { Component, OnInit } from '@angular/core';
import { Player } from '../../../models/player.model';
import { PlayerService } from '../../../services/player.service';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-players',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterLink],
  templateUrl: './players.component.html',
  styleUrl: './players.component.scss'
})
export class PlayersComponent implements OnInit {
  players: Player[] = [];

  constructor(private playerService: PlayerService) {}

  ngOnInit() {
  }

  loadPlayers() {
  }

  deletePlayer(id: number) {

  }
}
