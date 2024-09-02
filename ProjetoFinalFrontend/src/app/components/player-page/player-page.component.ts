import { Component, OnInit } from '@angular/core';
import { Coach } from '../../models/coach.model';
import { Team } from '../../models/team.model';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";
import { MenuComponent } from "../menu/menu.component";
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import { FooterComponent } from "../footer/footer.component";
import { Player } from '../../models/player.model';
import { PlayerService } from '../../services/player.service';

@Component({
  selector: 'app-player-page',
  standalone: true,
  imports: [CommonModule, HeaderComponent, MenuComponent, MenuLateralComponent, FooterComponent],
  templateUrl: './player-page.component.html',
  styleUrl: './player-page.component.scss'
})
export class PlayerPageComponent implements OnInit {
  players: Player[] = [];

  photo: string = 'https://static.vecteezy.com/system/resources/previews/018/765/757/original/user-profile-icon-in-flat-style-member-avatar-illustration-on-isolated-background-human-permission-sign-business-concept-vector.jpg';

  constructor(private playerService: PlayerService) {}

  ngOnInit(): void {
    this.loadPlayers();
  }

   loadPlayers(): void {
    this.playerService.getAllPlayers().subscribe({
      next: (players: Player[]) => {
        this.players = players;
      },
      error: (err) => console.error('Erro ao carregar jogadores:', err)
    });
  }

  verPerfil(player: Player): void {
    // Lógica para abrir modal de perfil do jogador
    console.log(player);
  }

  convidarParaTime(player: Player): void {
    // Lógica para enviar convite ao jogador
    console.log(`Convidando ${player.nickname} para o time`);
  }

  enviarMensagem(player: Player): void {
    // Lógica para enviar mensagem ao jogador
    console.log(`Enviando mensagem para ${player.nickname}`);
  }
}
