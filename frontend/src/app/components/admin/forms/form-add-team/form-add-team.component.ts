import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { TeamService } from '../../../../services/team.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form-add-team',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-add-team.component.html',
  styleUrl: './form-add-team.component.scss'
})
export class FormAddTeamComponent implements OnInit {
  teamForm!: FormGroup;

  constructor(private fb: FormBuilder, private service: TeamService,private router: Router) {}

  ngOnInit(): void {
    this.teamForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      gym: ['', Validators.required],
      foundation: ['', Validators.required],
      emailContact: ['', [Validators.required, Validators.email]],
      phoneContact: ['', Validators.required],
      coachId: ['',]
    });
  }

  onSubmitTeam(): void {
    if (this.teamForm.valid) {
      const coachId: number = this.teamForm.value.coachId;
      const teamData = this.teamForm.value;
      if(coachId !== null && coachId !== undefined) {
        teamData.coachId = coachId;
      }
      this.service.addTeam(teamData).subscribe({
        next: () => {
          alert('Equipe criada com sucesso');
          this.router.navigate(['/admin/teams']);
        },
        error: (error) => {
          alert('Erro ao criar equipe' + error);
        }
      });
    }
  }
}
