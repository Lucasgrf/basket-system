import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { MenuComponent } from '../menu/menu.component';
import { HeaderComponent } from "../header/header.component";
import { MenuLateralComponent } from "../menu-lateral/menu-lateral.component";
import anime from 'animejs';

@Component({
  selector: 'app-initial-page',
  standalone: true,
  imports: [FooterComponent, MenuComponent, HeaderComponent, MenuLateralComponent],
  templateUrl: './initial-page.component.html',
  styleUrl: './initial-page.component.scss'
})
export class InitialPageComponent implements OnInit {
  menuOpen = false;

  ngOnInit() {
    // Inicializa a animação do menu
    this.animateMenu();
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
    this.animateMenu();
  }

  animateMenu() {
    anime({
      targets: '#mobile-menu',
      translateY: this.menuOpen ? 0 : '-100%',
      duration: 300,
      easing: 'easeInOutQuad'
    });
  }
}
