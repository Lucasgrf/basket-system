import { Component, OnInit } from '@angular/core';
import { Training } from '../../../models/training.model';
import { TrainingService } from '../../../services/training.service';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-trainings',
  standalone: true,
  imports: [DatePipe, CommonModule, RouterLink, HeaderComponent],
  templateUrl: './trainings.component.html',
  styleUrl: './trainings.component.scss'
})
export class TrainingsComponent implements OnInit {
  trainings: Training[] = [];

  constructor(private trainingService: TrainingService) {}

  ngOnInit() {
    this.loadTrainings();
  }

  loadTrainings() {

  }

  deleteTraining(id: number) {

  }
}
