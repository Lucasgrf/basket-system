import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-initial-page',
  standalone: true,
  imports: [FooterComponent, MenuComponent],
  templateUrl: './initial-page.component.html',
  styleUrl: './initial-page.component.scss'
})
export class InitialPageComponent {

}
