import { Component, OnInit } from '@angular/core';
import { Training } from '../../../models/training.model';
import { TrainingService } from '../../../services/training.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from "../header/header.component";
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-trainings',
  standalone: true,
  imports: [CommonModule, RouterLink, HeaderComponent, ConfirmationDialogComponent],
  templateUrl: './trainings.component.html',
  styleUrl: './trainings.component.scss'
})
export class TrainingsComponent implements OnInit {
  trainings: Training[] = [];
  selectedTrainingId: number | null = null;
  showModal: boolean = false;

  constructor(private trainingService: TrainingService) {}

  ngOnInit() {
    this.loadTrainings();
  }

  loadTrainings() {
    this.trainingService.getAllTrainings().subscribe(trainings => {
      this.trainings = trainings;
    });
  }

  openDeleteModal(trainingId: number): void {
    this.selectedTrainingId = trainingId;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.selectedTrainingId = null;
    this.showModal = false;
  }

  confirmDelete(): void {
    if (this.selectedTrainingId !== null) {
      this.trainingService.deleteTraining(this.selectedTrainingId).subscribe({
        next: () => {
          this.loadTrainings(); // Recarrega a lista de treinamentos após exclusão
          this.closeDeleteModal();
        },
        error: (error) => {
          console.error('Erro ao excluir o treinamento', error);
          this.closeDeleteModal();
        }
      });
    }
  }
}
