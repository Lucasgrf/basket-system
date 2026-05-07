import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import anime from 'animejs';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './menu-lateral.component.html',
  styleUrl: './menu-lateral.component.scss'
})
export class MenuLateralComponent implements OnInit {
  isSidebarOpen: boolean = true;
  role: string | null = '';

  ngOnInit() {
    this.role = localStorage.getItem('role');
  }

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;

    anime({
      targets: '#sidebar',
      translateX: this.isSidebarOpen ? 0 : '-100%',
      duration: 500,
      easing: 'easeInOutQuad',
    });
  }

  logout() {
    localStorage.clear();
    alert('Deslogado com sucesso!');
    window.location.href = '/';
  }

}
