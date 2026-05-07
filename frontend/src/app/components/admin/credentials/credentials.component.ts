import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { CredentialService } from '../../../services/credential.service';
import { RouterLink } from '@angular/router';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { Credential } from '../../../models/credential.model';

@Component({
  selector: 'app-credentials',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, CommonModule, RouterLink, ConfirmationDialogComponent],
  templateUrl: './credentials.component.html',
  styleUrl: './credentials.component.scss'
})
export class CredentialsComponent implements OnInit {
  credentials: Credential[] = [];
  showModal = false;
  selectedCredentialId: number | null = null;

  constructor(private credentialService: CredentialService) { }

  ngOnInit(): void {
    this.fetchCredentials();
  }

  fetchCredentials(): void {
    this.credentialService.getAll().subscribe(data => {
      this.credentials = data;
    });
  }

  openDeleteModal(id: number): void {
    this.selectedCredentialId = id;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.showModal = false;
    this.selectedCredentialId = null;
  }

  confirmDelete(): void {
    if (this.selectedCredentialId !== null) {
      this.credentialService.delete(this.selectedCredentialId).subscribe(() => {
        this.fetchCredentials();
        this.closeDeleteModal();
      });
    }
  }
}
