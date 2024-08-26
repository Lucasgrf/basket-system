import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CredentialService } from '../../../../services/credential.service';
import { HeaderComponent } from '../../header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-update-credential',
  standalone: true,
  imports: [HeaderComponent, CommonModule, ReactiveFormsModule,RouterLink],
  templateUrl: './form-update-credential.component.html',
  styleUrl: './form-update-credential.component.scss'
})
export class FormUpdateCredentialComponent implements OnInit {
  credentialForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private credentialService: CredentialService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.credentialForm = this.fb.group({
      photoName: ['',],
      name: ['', Validators.required],
      teamId: ['',],
      userType: ['', Validators.required],
      userId: ['', Validators.required]
    });
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadCredential(id);
  }

  loadCredential(id: number) {
    this.credentialService.getCredentialById(id).subscribe(credential => {
      this.credentialForm.patchValue(credential);
    });
  }

  onSubmitCredential() {
    if (this.credentialForm.valid) {
      const id: number = +this.route.snapshot.paramMap.get('id')!;
      this.credentialService.update(id, this.credentialForm.value).subscribe({
        next: (credential) => {
          alert('Credencial atualizado com sucesso!');
          this.router.navigate(['/admin/users/credentials']);
        },
        error: (error) => {
          alert('Erro ao tentar atualizar credencial!' + error);
        }
      });
    }
  }

}
