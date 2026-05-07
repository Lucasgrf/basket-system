import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  isMenuOpen = false;

  toggleMenuHeader(): void {
    this.isMenuOpen = !this.isMenuOpen;

    if (this.isMenuOpen) {
      this.openMenuAnimation();
    } else {
      this.closeMenuAnimation();
    }
  }

  openMenuAnimation(): void {
    anime({
      targets: 'nav',
      translateX: ['-100%', '0%'],
      easing: 'easeOutExpo',
      duration: 500
    });
  }

  closeMenuAnimation(): void {
    anime({
      targets: 'nav',
      translateX: ['0%', '-100%'],
      easing: 'easeInExpo',
      duration: 500
    });
  }

  logout(): void {
    localStorage.clear();
    alert('Você foi desconectado');
    window.location.href = '';
  }
}
