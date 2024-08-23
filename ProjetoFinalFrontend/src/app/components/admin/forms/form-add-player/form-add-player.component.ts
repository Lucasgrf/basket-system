import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-add-player',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './form-add-player.component.html',
  styleUrl: './form-add-player.component.scss'
})
export class FormAddPlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
