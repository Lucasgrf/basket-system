import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-delete-player',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-delete-player.component.html',
  styleUrl: './form-delete-player.component.scss'
})
export class FormDeletePlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
