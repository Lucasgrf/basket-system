import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { TrainingService } from '../../../../services/training.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form-add-training',
  standalone: true,
  providers: [DatePipe],
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-add-training.component.html',
  styleUrl: './form-add-training.component.scss'
})
export class FormAddTrainingComponent {
  trainingForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private trainingService: TrainingService,
    private datePipe: DatePipe,
    private router: Router
  ){}

  ngOnInit(): void {
    this.trainingForm = this.fb.group({
      title: ['', Validators.required],
      date: ['', Validators.required],
      location: ['', Validators.required],
      teamId: ['', Validators.required]
    });
  }

  onSubmitTraining(): void {
    if (this.trainingForm.valid) {
      const trainingData = this.trainingForm.value;
      // Formatar a data antes de enviar
      trainingData.dateTime = this.datePipe.transform(trainingData.dateTime, 'dd/MM/yyyy HH:mm');
      console.log(trainingData);
      this.trainingService.addTraining(trainingData).subscribe({
        next: () => {
          alert('Treino salvo com sucesso');
          this.trainingForm.reset();
        },
        error: (error) => {
          alert('Erro ao salvar o treino' + error);
        }
      });
    } else {
      console.error('Formulário inválido');
    }
  }

}
