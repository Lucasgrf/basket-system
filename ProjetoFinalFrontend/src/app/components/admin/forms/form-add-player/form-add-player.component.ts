import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { CommonModule } from '@angular/common';
import { PlayerService } from '../../../../services/player.service';

@Component({
  selector: 'app-form-add-player',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, CommonModule],
  templateUrl: './form-add-player.component.html',
  styleUrl: './form-add-player.component.scss'
})
export class FormAddPlayerComponent {
  playerForm!: FormGroup;
  heights: string[] = [];
  weights: string[] = [];

  constructor(private fb: FormBuilder, private service: PlayerService) {}

  ngOnInit() {
    this.generateHeights();
    this.generateWeights();
    this.playerForm = this.fb.group({
      position: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(1)]],
      height: ['', Validators.required],
      weight: ['', Validators.required],
      teamId: ['', Validators.required],
      userId: ['', Validators.required]
    });
  }

  generateHeights() {
    const start = 1.60;
    const end = 2.30;
    const step = 0.05;
    for (let height = start; height <= end; height += step) {
      this.heights.push(height.toFixed(2));
    }
  }

  generateWeights() {
    const start = 50;
    const end = 200;
    const step = 1;
    for (let weight = start; weight <= end; weight += step) {
      this.weights.push(weight.toString());
    }
  }

  onHeightInput(event: Event) {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    if (this.heights.includes(value)) {
      this.playerForm.controls['height'].setValue(value);
    }
  }

  onWeightInput(event: Event) {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    if (this.weights.includes(value)) {
      this.playerForm.controls['weight'].setValue(value);
    }
  }

  onSubmitPlayer() {
    if (this.playerForm.valid) {
      this.service.createPlayer(this.playerForm.value).subscribe({
        next: () => {
          alert('Jogador criado com sucesso');
        },
        error: (error) => {
          alert('Erro ao criar jogador');
          console.log(error);
        }
      });
    }
  }

}
