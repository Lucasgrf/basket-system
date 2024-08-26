import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../header/header.component";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TeamService } from '../../../../services/team.service';

@Component({
  selector: 'app-form-update-team',
  standalone: true,
  providers: [DatePipe],
  imports: [CommonModule, HeaderComponent,ReactiveFormsModule, RouterLink],
  templateUrl: './form-update-team.component.html',
  styleUrl: './form-update-team.component.scss'
})
export class FormUpdateTeamComponent implements OnInit {
  teamForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: TeamService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private router: Router
  ) {
    this.teamForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      gym: ['', Validators.required],
      foundation: ['', Validators.required],
      emailContact: ['', [Validators.required, Validators.email]],
      phoneContact: ['', Validators.required],
      coachId: ['', Validators.required]
    });
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadTeam(id);
  }

  loadTeam(id: number) {
    // Carregando os dados do time e atualizando o formulário
    this.service.getTeamById(id).subscribe(team => {
      if (team.foundation) {
        // Formatando a data para o formato esperado pelo input date
        team.foundation = this.datePipe.transform(team.foundation, 'yyyy-MM-dd');
      }
      this.teamForm.patchValue(team);
    });
  }

  onSubmitTeam() {
    if (this.teamForm.valid) {
      const id: number = +this.route.snapshot.paramMap.get('id')!;
      const teamData = this.teamForm.value;
      this.service.updateTeam(id, teamData).subscribe({
        next: (team) => {
          alert('Time atualizado com sucesso');
          this.router.navigate(['/admin/teams']);
        },
        error: (error) => {
          alert('Erro ao atualizar o time' + error);
        }
      });
    } else {
      console.error('Formulário inválido');
    }
  }

}
