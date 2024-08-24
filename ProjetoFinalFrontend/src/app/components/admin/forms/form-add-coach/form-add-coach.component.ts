import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-add-coach',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-add-coach.component.html',
  styleUrl: './form-add-coach.component.scss'
})
export class FormAddCoachComponent {
onSubmitUser() {
throw new Error('Method not implemented.');
}
  coach: any = {};
}
