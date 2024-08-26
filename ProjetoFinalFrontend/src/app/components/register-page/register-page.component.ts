import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FooterComponent } from "../footer/footer.component";
import anime from 'animejs';

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FooterComponent, RouterLink],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.scss'
})
export class RegisterPageComponent implements AfterViewInit {
  private fb = inject(FormBuilder);
  submitted = false;

  constructor(private router: Router, private service: AuthService) {}

  registerForm = this.fb.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirmPassword: ['', [Validators.required]],
    role: ['', [Validators.required]]
  });

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.valid) {
      if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
        alert('As senhas não conferem');
        return;
      }

      const { confirmPassword, ...registerData } = this.registerForm.value;

      this.service.signup(registerData.username!, registerData.email!, registerData.password!, registerData.role!).subscribe({
        next: () => {
          alert('Cadastro realizado com sucesso');
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          alert('Erro ao realizar cadastro, tente novamente' + error);
        }
      });
    }
  }

  ngAfterViewInit() {
    // Animação usando Anime.js
    anime({
      targets: '.form-container',
      opacity: [0, 1],
      translateY: [-50, 0],
      duration: 1000,
      easing: 'easeOutExpo',
    });
  }
}
