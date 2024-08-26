import { Component, ElementRef, ViewChild } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import anime from 'animejs';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  imports: [FooterComponent, RouterLink, CommonModule, FormsModule],
  templateUrl: './contact-us.component.html',
  styleUrl: './contact-us.component.scss'
})
export class ContactUsComponent {
  @ViewChild('menuBox') menuBox!: ElementRef;
  form!: NgForm;

  isMenuOpen = false;

  constructor(private route: Router) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.animateMenu();
    this.animateBackground();
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
      targets: '.animate-circle',
      translateY: [
        { value: -20, duration: 1000, easing: 'easeInOutSine' },
        { value: 0, duration: 1000, easing: 'easeInOutSine' }
      ],
      loop: true
    });

    anime({
      targets: '.animate-path',
      strokeDashoffset: [anime.setDashoffset, 0],
      duration: 10000,
      easing: 'easeInOutSine',
      loop: true
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log('Formulário enviado:', this.form.value);
      alert('Formulário enviado com sucesso!');
      this.route.navigate(['/']);
    }
  }
}
