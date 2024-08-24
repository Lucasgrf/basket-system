import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-delete-player',
  standalone: true,
  imports: [FormsModule, HeaderComponent],
  templateUrl: './form-delete-player.component.html',
  styleUrl: './form-delete-player.component.scss'
})
export class FormDeletePlayerComponent {
player: any;
onSubmitUser() {
throw new Error('Method not implemented.');
}

}
