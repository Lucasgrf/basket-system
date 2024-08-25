import { CommonModule } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-admin-header',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements AfterViewInit {
  isMenuOpen = false;

  constructor() { }

  ngAfterViewInit(): void {
    this.animateDesktopMenu();
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
    this.animateMobileMenu();
  }

  private animateDesktopMenu(): void {
    anime({
      targets: '#desktop-menu',
      opacity: [0, 1],
      translateY: [-50, 0],
      easing: 'easeOutQuad',
      duration: 800
    });
  }

  private animateMobileMenu(): void {
    const mobileMenu = document.getElementById('mobile-menu');
    if (mobileMenu) {
      if (this.isMenuOpen) {
        anime({
          targets: mobileMenu,
          opacity: [0, 1],
          translateX: [50, 0],
          easing: 'easeOutQuad',
          duration: 500
        });
      } else {
        anime({
          targets: mobileMenu,
          opacity: [1, 0],
          translateX: [0, 50],
          easing: 'easeOutQuad',
          duration: 500
        });
      }
    }
  }
}
