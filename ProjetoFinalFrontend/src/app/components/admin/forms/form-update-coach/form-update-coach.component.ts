import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { Router, ActivatedRoute } from '@angular/router';
import { CoachService } from '../../../../services/coach.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-update-coach',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, CommonModule],
  templateUrl: './form-update-coach.component.html',
  styleUrl: './form-update-coach.component.scss'
})
export class FormUpdateCoachComponent {
  coachForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private coachService: CoachService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.coachForm = this.fb.group({
      nickname: ['', ],
      teamId: ['', ],
      userId: ['', ]
    });
  }

  ngOnInit(): void {
    const coachId = +this.route.snapshot.paramMap.get('id')!;
    this.coachService.getCoach(coachId).subscribe(coach => {
      this.coachForm.patchValue(coach);
    });
  }

  onSubmit(): void {
    if (this.coachForm.valid) {
      const id = +this.route.snapshot.paramMap.get('id')!;
      this.coachService.updateCoach(id, this.coachForm.value).subscribe(() => {
        alert('Coach atualizado com sucesso!');
        this.router.navigate(['/admin/coaches']);
      }, (error: any) => {
        console.log(error);
        alert('Erro ao atualizar coach!');
      });
    }
  }

}
