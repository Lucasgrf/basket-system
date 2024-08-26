import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../header/header.component";
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HeaderComponent],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.scss'
})
export class ProfilePageComponent implements OnInit {
  profileForm!: FormGroup;
  user!: User;
  userId = parseInt(localStorage.getItem('userId') || '0');

  constructor(private fb: FormBuilder, private service: UserService, private route: Router) {
    {
      this.profileForm = this.fb.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['',],
      });
    }
  }

  ngOnInit(): void {
    this.loadUser(this.userId);
  }

  saveProfile() {
    if (this.profileForm.valid) {
      this.service.updateProfile(this.userId, this.profileForm.value).subscribe({
        next: () => {
          alert('Perfil atualizado com sucesso');
          this.route.navigate(['/dashboard']);
        },
        error: (error) => {
          alert('Erro ao atualizar perfil' + error);
        }
      });
    } else {
      console.log('Formulário inválido');
    }
  }

  loadUser(userId?: number): void {
    // Fetch user data and patch the form
    this.service.getUserById(this.userId).subscribe(user => {
      this.user = user;
      this.profileForm.patchValue({ username: user.username, email: user.email });
    });
  }
}
