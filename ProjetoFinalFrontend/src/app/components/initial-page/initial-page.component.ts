import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from '../menu/menu.component';
import { HeaderComponent } from "../header/header.component";
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";

@Component({
  selector: 'app-initial-page',
  standalone: true,
  imports: [FooterComponent, MenuComponent, HeaderComponent, MenuLateralComponent],
  templateUrl: './initial-page.component.html',
  styleUrl: './initial-page.component.scss'
})
export class InitialPageComponent {

}
