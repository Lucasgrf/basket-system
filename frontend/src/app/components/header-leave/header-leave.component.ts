import { CommonModule } from '@angular/common';
import { Component, ElementRef, AfterViewInit, OnInit, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-header-leave',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './header-leave.component.html',
  styleUrl: './header-leave.component.scss'
})
export class HeaderLeaveComponent implements OnInit, AfterViewInit {
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
