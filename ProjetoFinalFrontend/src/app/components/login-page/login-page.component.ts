import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FooterComponent } from "../footer/footer.component";
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [ReactiveFormsModule, FooterComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
  private fb = inject(FormBuilder);

  constructor(
    private service: AuthService,
    private router: Router
  ) { }

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  })

  onSubmit() {
      this.service.login(this.loginForm.value).subscribe({
        next: () => {
          alert("Login Successful");
          this.router.navigate(['home/dashboard']);
        },
        error: (error) => {
          alert("Login Failed, Please try again");
          console.log(error);
        }
      })
  }

}
