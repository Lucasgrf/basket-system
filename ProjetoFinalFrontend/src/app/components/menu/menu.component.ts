import { CommonModule } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements AfterViewInit {

  ngAfterViewInit() {
    this.addAnimations();
  }

  addAnimations() {
    anime({
      targets: '.menu-item',
      scale: [1, 1.1],
      opacity: [1, 0.8],
      easing: 'easeInOutQuad',
      duration: 1200,
      delay: anime.stagger(500, { start: 100 }),
      loop: true,
      direction: 'alternate'
    });
  }
}
