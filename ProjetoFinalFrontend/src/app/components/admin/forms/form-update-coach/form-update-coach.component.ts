import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-update-coach',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-update-coach.component.html',
  styleUrl: './form-update-coach.component.scss'
})
export class FormUpdateCoachComponent {
coach: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
