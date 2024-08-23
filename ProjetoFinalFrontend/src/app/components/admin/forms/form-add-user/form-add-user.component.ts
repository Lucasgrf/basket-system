import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-add-user',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-add-user.component.html',
  styleUrl: './form-add-user.component.scss'
})
export class FormAddUserComponent {
  user: any = {};

  onSubmitUser() {
    throw new Error('Method not implemented.');
  }
}
