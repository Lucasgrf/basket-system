import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../../services/user.service';
import { HeaderComponent } from "../../header/header.component";

@Component({
  selector: 'app-form-update-user',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './form-update-user.component.html',
  styleUrl: './form-update-user.component.scss'
})
export class FormUpdateUserComponent implements OnInit {
  userForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['',],
      role: ['', Validators.required]
    });
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadUser(id);
  }

  loadUser(id: number) {
    // Fetch user data and patch the form
    this.userService.getUserById(id).subscribe(user => {
      this.userForm.patchValue(user);
    });
  }

  onSubmitUser() {
    if (this.userForm.valid) {
      const id: number = +this.route.snapshot.paramMap.get('id')!;
      this.userService.updateUser(id, this.userForm.value).subscribe({
        next: (user) => {
          alert('Usuário atualizado com sucesso');
          this.router.navigate(['/admin/users']);
        },
        error: (error) => {
          alert('Erro ao atualizar usuário' + error);
        }
      });
    }
  }
}
