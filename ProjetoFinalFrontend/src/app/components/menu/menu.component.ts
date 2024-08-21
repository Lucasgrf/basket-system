import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  isMobile: boolean = false;

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.isMobile = window.innerWidth < 768; // Ajuste conforme necessário
  }

  toggleMenu(): void {
    this.isMobile = !this.isMobile;
  }

  ngOnInit(): void {
    this.isMobile = window.innerWidth < 768;
  }
}
