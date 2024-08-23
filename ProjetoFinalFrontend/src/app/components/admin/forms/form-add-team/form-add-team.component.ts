import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-add-team',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-add-team.component.html',
  styleUrl: './form-add-team.component.scss'
})
export class FormAddTeamComponent {
team: any;

onSubmitTeam() {
throw new Error('Method not implemented.');
}

}
