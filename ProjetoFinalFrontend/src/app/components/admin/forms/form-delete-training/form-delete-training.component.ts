import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-delete-training',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-delete-training.component.html',
  styleUrl: './form-delete-training.component.scss'
})
export class FormDeleteTrainingComponent {
training: any;
onSubmitTraining() {
throw new Error('Method not implemented.');
}

}
