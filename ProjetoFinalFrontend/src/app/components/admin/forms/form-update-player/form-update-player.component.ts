import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-update-player',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-update-player.component.html',
  styleUrl: './form-update-player.component.scss'
})
export class FormUpdatePlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
