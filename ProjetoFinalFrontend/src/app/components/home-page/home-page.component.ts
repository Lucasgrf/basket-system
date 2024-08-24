import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [FooterComponent, CommonModule, MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {
  isMenuOpen = false;

  @ViewChild('menuBox', { static: false }) menuBox!: ElementRef;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    this.animateMenu();
  }

  animateMenu() {
    if (this.menuBox) {
      const menuElement = this.menuBox.nativeElement;
      if (this.isMenuOpen) {
        anime({
          targets: menuElement,
          translateX: ['100%', '0%'],
          opacity: [0, 1],
          duration: 300,
          easing: 'easeInOutCubic'
        });
      } else {
        anime({
          targets: menuElement,
          translateX: ['0%', '100%'],
          opacity: [1, 0],
          duration: 300,
          easing: 'easeInOutCubic'
        });
      }
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    if (window.innerWidth >= 1024) {
      this.isMenuOpen = false;
      this.animateMenu();
    }
  }
}
