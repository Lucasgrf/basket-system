import { Component, ElementRef, ViewChild } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import anime from 'animejs';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  imports: [FooterComponent, RouterLink, CommonModule, ReactiveFormsModule],
  templateUrl: './contact-us.component.html',
  styleUrl: './contact-us.component.scss'
})
export class ContactUsComponent {
  @ViewChild('menuBox') menuBox!: ElementRef;
  contactForm!: FormGroup;
  isMenuOpen = false;

  constructor(private route: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.contactForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      message: ['', Validators.required],
    });
  }

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
      targets: '#background-shapes circle, #background-shapes path',
      strokeDashoffset: [anime.setDashoffset, 0],
      easing: 'easeInOutSine',
      duration: 2000,
      loop: true,
      direction: 'alternate'
    });
  }

  onSubmit(): void {
    if (this.contactForm.valid) {
      const { name, email, message } = this.contactForm.value;
      console.log('Formulário enviado com sucesso:', { name, email, message });
      alert('Formulário enviado com sucesso!');
      this.route.navigate(['/']);
      //this.contactForm.reset();
    } else {
      alert('Formulário inválido, verifique os campos e tente novamente.');
    }
  }
}
