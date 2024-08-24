import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-delete-team',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-delete-team.component.html',
  styleUrl: './form-delete-team.component.scss'
})
export class FormDeleteTeamComponent {
team: any;
onSubmitTeam() {
throw new Error('Method not implemented.');
}

}
