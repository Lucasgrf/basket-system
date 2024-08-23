import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-update-user',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-update-user.component.html',
  styleUrl: './form-update-user.component.scss'
})
export class FormUpdateUserComponent {
  user: any = {};

  onSubmitUser() {
    throw new Error('Method not implemented.');
  }
}
