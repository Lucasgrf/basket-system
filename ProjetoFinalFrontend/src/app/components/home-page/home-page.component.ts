import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
export class HomePageComponent implements OnInit, AfterViewInit {
  @ViewChild('menuBox') menuBox!: ElementRef;

  isMenuOpen = false;

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.animateMenu();
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
    this.animateMenu();
  }

  animateMenu(): void {
    anime({
      targets: this.menuBox.nativeElement,
      opacity: this.isMenuOpen ? [0, 1] : [1, 0],
      translateX: this.isMenuOpen ? ['100%', '0%'] : ['0%', '100%'],
      easing: 'easeInOutQuad',
      duration: 300,
    });
  }
}
