import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-add-player',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-add-player.component.html',
  styleUrl: './form-add-player.component.scss'
})
export class FormAddPlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
