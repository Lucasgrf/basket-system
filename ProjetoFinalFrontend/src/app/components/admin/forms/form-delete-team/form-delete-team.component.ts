import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-delete-team',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-delete-team.component.html',
  styleUrl: './form-delete-team.component.scss'
})
export class FormDeleteTeamComponent {
team: any;
onSubmitTeam() {
throw new Error('Method not implemented.');
}

}
