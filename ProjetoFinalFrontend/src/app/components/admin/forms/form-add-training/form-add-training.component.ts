import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-add-training',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-add-training.component.html',
  styleUrl: './form-add-training.component.scss'
})
export class FormAddTrainingComponent {
training: any;
onSubmitTraining() {
throw new Error('Method not implemented.');
}

}
