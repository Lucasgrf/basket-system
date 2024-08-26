import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './menu-lateral.component.html',
  styleUrl: './menu-lateral.component.scss'
})
export class MenuLateralComponent {
  isOpen = false;

  toggleMenu() {
    this.isOpen = !this.isOpen;
    this.animateMenu();
  }

  private animateMenu() {
    anime({
      targets: '.sidebar',
      easing: 'easeInOutQuad',
      duration: 300,
      translateX: this.isOpen ? ['-100%', '0%'] : ['0%', '-100%']
    });
  }
}
