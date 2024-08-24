import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from "../header/header.component";
import { UserService } from '../../../services/user.service';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, RouterLink, HeaderComponent,ConfirmationDialogComponent],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  selectedUserId: number | null = null;
  showModal: boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

  openDeleteModal(userId: number): void {
    this.selectedUserId = userId;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.selectedUserId = null;
    this.showModal = false;
  }

  confirmDelete(): void {
    if (this.selectedUserId !== null) {
      this.userService.deleteUser(this.selectedUserId).subscribe({
        next: () => {
          this.loadUsers(); // Recarregar a lista de usuários após exclusão
          this.closeDeleteModal();
        },
        error: (error) => {
          console.error('Erro ao excluir usuário', error);
          this.closeDeleteModal();
        }
      });
    }
  }
}
