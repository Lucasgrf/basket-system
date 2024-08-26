import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { ActivatedRoute, Router } from '@angular/router';
import { PlayerService } from '../../../../services/player.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-update-player',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent,CommonModule],
  templateUrl: './form-update-player.component.html',
  styleUrl: './form-update-player.component.scss'
})
export class FormUpdatePlayerComponent implements OnInit {
  playerForm: FormGroup;
  heights: string[] = [];
  weights: string[] = [];

  constructor(
    private fb: FormBuilder,
    private playerService: PlayerService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.playerForm = this.fb.group({
      position: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(16), Validators.max(45)]],
      height: ['', Validators.required],
      weight: ['', Validators.required],
      teamId: [''],
      userId: ['', Validators.required],
    });

    this.heights = this.generateRange(1.6, 2.3, 0.01);
    this.weights = this.generateRange(50, 180, 0.1);
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadPlayer(id);
  }

  generateRange(start: number, end: number, step: number): string[] {
    const range: string[] = [];
    for (let i = start; i <= end; i += step) {
      range.push(i.toFixed(2));
    }
    return range;
  }

  loadPlayer(id: number) {
    this.playerService.getPlayerById(id).subscribe((player) => {
      this.playerForm.patchValue(player);
    });
  }

  onSubmitPlayer() {
    if (this.playerForm.valid) {
      const id: number = +this.route.snapshot.paramMap.get('id')!;
      console.log(this.playerForm.value);
      this.playerService.updatePlayer(id, this.playerForm.value).subscribe({
        next: (player) => {
          alert('Jogador atualizado com sucesso!');
          this.router.navigate(['/admin/players']);
        },
        error: (error) => {
          alert('Erro ao tentar atualizar o jogador!' + error);
        },
      });
    }
  }

}
