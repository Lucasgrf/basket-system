import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../../header/header.component";
import { UserService } from '../../../../services/user.service';

@Component({
  selector: 'app-form-add-user',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-add-user.component.html',
  styleUrl: './form-add-user.component.scss'
})
export class FormAddUserComponent {
  userForm: FormGroup;

  constructor(private fb: FormBuilder, private service: UserService) {
    // Inicialize o FormGroup com as validações necessárias
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  onSubmitUser() {
    if (this.userForm.valid) {
      const savePassword = this.userForm.value.password;
      const { password, ...formData } = this.userForm.value;
      this.service.createUser(formData, savePassword).subscribe({
        next: (user) => {
          alert('Usuário criado com sucesso');
        },
        error: (error) => {
          alert('Erro ao criar usuário');
          alert(error);
        }
      });
    }
  }
}

