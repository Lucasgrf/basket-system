import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-update-training',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-update-training.component.html',
  styleUrl: './form-update-training.component.scss'
})
export class FormUpdateTrainingComponent {
team: any;
onSubmitTeam() {
throw new Error('Method not implemented.');
}

}
