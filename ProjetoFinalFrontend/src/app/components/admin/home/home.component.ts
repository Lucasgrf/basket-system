
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../footer/footer.component";
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import anime from 'animejs';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [FooterComponent, CommonModule, RouterLink, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements AfterViewInit {
  isMenuOpen = false;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.animateCards();
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  animateCards() {
    anime({
      targets: '.card',
      scale: [0.8, 1],
      opacity: [0, 1],
      easing: 'easeOutBack',
      duration: 800,
      delay: anime.stagger(200)
    });
  }
}
