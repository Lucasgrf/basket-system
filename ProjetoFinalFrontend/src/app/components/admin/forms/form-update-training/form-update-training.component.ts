import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-update-training',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-update-training.component.html',
  styleUrl: './form-update-training.component.scss'
})
export class FormUpdateTrainingComponent {
team: any;
onSubmitTeam() {
throw new Error('Method not implemented.');
}

}
