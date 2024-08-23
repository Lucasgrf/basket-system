import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-delete-user',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-delete-user.component.html',
  styleUrl: './form-delete-user.component.scss'
})
export class FormDeleteUserComponent {
  user: any = {};

  onSubmitUser() {
    throw new Error('Method not implemented.');
  }
}
