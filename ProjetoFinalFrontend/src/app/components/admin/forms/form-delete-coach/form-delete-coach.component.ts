import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-delete-coach',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-delete-coach.component.html',
  styleUrl: './form-delete-coach.component.scss'
})
export class FormDeleteCoachComponent {
coach: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
