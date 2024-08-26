import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { RouterLink } from '@angular/router';
import { HeaderLeaveComponent } from "../header-leave/header-leave.component";
import { CommonModule } from '@angular/common';
import anime from 'animejs';

@Component({
  selector: 'app-about-page',
  standalone: true,
  imports: [FooterComponent, RouterLink, HeaderLeaveComponent, CommonModule],
  templateUrl: './about-page.component.html',
  styleUrl: './about-page.component.scss'
})
export class AboutPageComponent implements OnInit, AfterViewInit {
  @ViewChild('menuBox') menuBox!: ElementRef;

  isMenuOpen = false;

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.animateBackground();
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

  animateBackground(): void {
    anime({
      targets: '#background-shapes circle, #background-shapes path',
      strokeDashoffset: [anime.setDashoffset, 0],
      easing: 'easeInOutSine',
      duration: 2000,
      loop: true,
      direction: 'alternate'
    });
  }
}
