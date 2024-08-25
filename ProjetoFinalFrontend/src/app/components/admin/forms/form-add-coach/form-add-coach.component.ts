import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { CoachService } from '../../../../services/coach.service';

@Component({
  selector: 'app-form-add-coach',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-add-coach.component.html',
  styleUrl: './form-add-coach.component.scss'
})
export class FormAddCoachComponent {
  coachForm: FormGroup;

  constructor(private fb: FormBuilder, private service: CoachService) {
    // Inicialize o FormGroup com as validações necessárias
    this.coachForm = this.fb.group({
      nickname: ['', Validators.required],
      teamId: ['', ],
      userId: ['', Validators.required]
    });
  }

  onSubmitCoach() {
    if (this.coachForm.valid) {
      const formData = this.coachForm.value;
      console.log(formData);
      this.service.createCoach(formData).subscribe({
        next: (coach) => {
          alert('Treinador criado com sucesso');
          console.log(coach);
        },
        error: (error) => {
          alert('Erro ao criar treinador');
          alert(error);
        }
      });
    }
  }
}
