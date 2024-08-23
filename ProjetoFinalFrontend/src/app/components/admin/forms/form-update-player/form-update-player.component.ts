import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-update-player',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-update-player.component.html',
  styleUrl: './form-update-player.component.scss'
})
export class FormUpdatePlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
