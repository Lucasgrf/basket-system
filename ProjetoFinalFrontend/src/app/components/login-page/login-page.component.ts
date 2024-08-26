import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FooterComponent } from "../footer/footer.component";
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import anime from 'animejs';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [ReactiveFormsModule, FooterComponent, CommonModule, RouterLink],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
  private fb = inject(FormBuilder);

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  constructor(
    private service: AuthService,
    private router: Router
  ) {}

  ngAfterViewInit() {
    this.initializeAnimations();
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.service.login(this.loginForm.value).subscribe({
        next: () => {
          alert("Login bem-sucedido!");
          this.router.navigate(['home/dashboard']);
        },
        error: (error) => {
          alert("Falha no login, tente novamente.");
          console.log(error);
        }
      });
    }
  }

  private initializeAnimations() {
    anime({
      targets: '.login-bg',
      opacity: [0, 1],
      duration: 2000,
      easing: 'easeInOutQuad'
    });

    anime({
      targets: '.bg-gray-900',
      scale: [1.1, 1],
      duration: 1500,
      easing: 'easeInOutQuad'
    });
  }

}
