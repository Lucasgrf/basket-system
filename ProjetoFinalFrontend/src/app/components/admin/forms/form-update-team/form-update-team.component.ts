import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { HeaderComponent } from "../../header/header.component";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-update-team',
  standalone: true,
  imports: [CommonModule, HeaderComponent,FormsModule],
  templateUrl: './form-update-team.component.html',
  styleUrl: './form-update-team.component.scss'
})
export class FormUpdateTeamComponent {
onSubmitTeam() {
throw new Error('Method not implemented.');
}
team: any;

}
