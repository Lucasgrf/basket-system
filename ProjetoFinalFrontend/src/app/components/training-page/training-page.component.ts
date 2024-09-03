import { Component, OnInit } from '@angular/core';
import { Training } from '../../models/training.model';
import { TrainingService } from '../../services/training.service';
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from "../menu/menu.component";
import { HeaderComponent } from "../header/header.component";
import { CommonModule } from '@angular/common';
import { PlayerService } from '../../services/player.service';
import { Player } from '../../models/player.model';
import { CoachService } from '../../services/coach.service';
import { Coach } from '../../models/coach.model';

@Component({
  selector: 'app-training-page',
  standalone: true,
  imports: [CommonModule, MenuLateralComponent, FooterComponent, MenuComponent, HeaderComponent],
  templateUrl: './training-page.component.html',
  styleUrl: './training-page.component.scss'
})
export class TrainingPageComponent implements OnInit {
  trainings: Training[] = [];
  userRole: any;
  userTeamId: any;
  userId = parseInt(localStorage.getItem('userId') || '0');
  photo: string = 'https://www.pngitem.com/pimgs/m/481-4818558_agenda-icon-png-download-transparent-background-agenda-png.png';

  constructor(private trainingService: TrainingService, private playerService: PlayerService, private coachService: CoachService) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    console.log(this.userRole);
    if (this.userRole === 'PLAYER') {
      this.playerService.getPlayerByUserId(this.userId).subscribe((player: Player) => {
        this.userTeamId = player.teamId;
        console.log(this.userTeamId);
        this.loadTrainings();
      });
    } else {
      this.coachService.getCoachByUserId(this.userId).subscribe((coach: Coach) => {
        this.userTeamId = coach.teamId;
        console.log(this.userTeamId);
        this.loadTrainings();
      });
    }
  }

  loadTrainings(): void {
    this.trainingService.getTrainingsByTeam(this.userTeamId).subscribe((trainings: Training[]) => {
      this.trainings = trainings;
      console.log(this.trainings);
    });
  }

  isPastTraining(trainingDate: string): boolean {
    const trainingDateObj = new Date(trainingDate);
    const today = new Date();
    return trainingDateObj < today; // Verifica se o treino é anterior a hoje
  }

  confirmPresence(trainingId: number): void {
    console.log(`Presença confirmada para o treino ${trainingId}`);
    // Implementar lógica de envio ao backend
  }

  notifyAbsence(trainingId: number): void {
    console.log(`Aviso de ausência enviado para o treino ${trainingId}`);
    // Implementar lógica de envio ao backend
  }

  createTraining(): void {
    console.log(`Criando um novo treino...`);
    // Implementar lógica para abrir o formulário de criação de treino
  }

  viewTrainingDetails(arg0: number) {
    console.log(`Visualizando detalhes do treino ${arg0}`);
    // Implementar lógica para abrir a página de detalhes do treino
  }
}
