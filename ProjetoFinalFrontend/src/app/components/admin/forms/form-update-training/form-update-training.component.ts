import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { TrainingService } from '../../../../services/training.service';

@Component({
  selector: 'app-form-update-training',
  standalone: true,
  providers: [DatePipe],
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-update-training.component.html',
  styleUrl: './form-update-training.component.scss'
})
export class FormUpdateTrainingComponent {
  trainingForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private trainingService: TrainingService,
    private route: ActivatedRoute,
    private datePipe: DatePipe
  ) {
    this.trainingForm = this.fb.group({
      title: ['', Validators.required],
      date: ['', Validators.required],
      location: ['', Validators.required],
      teamId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadTraining(id);
  }

  loadTraining(id: number): void {
    this.trainingService.getTrainingById(id).subscribe(training => {
      if (training.date) {
        // Formatando a data para o formato esperado pelo input datetime-local
        training.date = this.datePipe.transform(training.date, 'yyyy-MM-ddTHH:mm');
      }
      this.trainingForm.patchValue({
        title: training.title,
        date: training.date,
        location: training.location,
        teamId: training.teamId
      });
    });
  }

  onSubmitTraining(): void {
    if (this.trainingForm.valid) {
      const id: number = +this.route.snapshot.paramMap.get('id')!;
      const trainingData = this.trainingForm.value;
      this.trainingService.updateTraining(id, trainingData).subscribe({
        next: (response) => {
          console.log('Treino atualizado com sucesso', response);
          alert('Treino atualizado com sucesso');
        },
        error: (error) => {
          console.error('Erro ao atualizar o treino', error);
          alert('Erro ao atualizar o treino');
        }
      });
    } else {
      console.error('Formulário inválido');
    }
  }

}
