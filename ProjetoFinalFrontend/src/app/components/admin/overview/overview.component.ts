import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../footer/footer.component";
import { CommonModule } from '@angular/common';
import anime from 'animejs';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [FooterComponent, CommonModule, HeaderComponent],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss'
})
export class OverviewComponent implements OnInit, AfterViewInit{
  isMenuOpen = false;
  totalUsers = 0; // Placeholder values, replace with actual data
  totalTeams = 0; // Placeholder values, replace with actual data
  totalTrainings = 0; // Placeholder values, replace with actual data

  constructor() { }

  ngOnInit(): void {
    // Simulate fetching data
    this.totalUsers = 1234; // Fetch actual data from a service
    this.totalTeams = 56; // Fetch actual data from a service
    this.totalTrainings = 78; // Fetch actual data from a service
  }

  ngAfterViewInit(): void {
    this.animateCards();
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  private animateCards(): void {
    anime({
      targets: '.card',
      opacity: [0, 1],
      translateY: [50, 0],
      easing: 'easeOutQuad',
      duration: 800,
      delay: anime.stagger(100) // Stagger animation delay for each card
    });
  }
}
