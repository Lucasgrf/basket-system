import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Training } from '../../../models/training.model';
import { TrainingDialogComponent } from '../../training-dialog/training-dialog.component';
import { TrainingService } from '../../../services/training.service';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-trainings',
  standalone: true,
  imports: [DatePipe,CommonModule, RouterLink],
  templateUrl: './trainings.component.html',
  styleUrl: './trainings.component.scss'
})
export class TrainingsComponent implements OnInit {
  trainings: Training[] = [];

  constructor(private trainingService: TrainingService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadTrainings();
  }

  loadTrainings() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(TrainingDialogComponent, {
      width: '600px',
      data: { action: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadTrainings();
      }
    });
  }

  openEditDialog(training: Training) {
    const dialogRef = this.dialog.open(TrainingDialogComponent, {
      width: '600px',
      data: { action: 'edit', training }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadTrainings();
      }
    });
  }

  deleteTraining(id: number) {

  }
}
