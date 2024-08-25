import { Component, OnInit} from '@angular/core';
import { Coach } from '../../../models/coach.model';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";
import { RouterLink } from '@angular/router';
import { CoachService } from '../../../services/coach.service';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-coaches',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterLink,ConfirmationDialogComponent],
  templateUrl: './coaches.component.html',
  styleUrl: './coaches.component.scss'
})
export class CoachesComponent implements OnInit {
  coaches: Coach[] = [];
  selectedCoachId: number | null = null;
  showModal: boolean = false;

  constructor(private coachService: CoachService) {}

  ngOnInit() {
    this.loadCoaches();
  }

  loadCoaches() {
    this.coachService.getAllCoaches().subscribe(coaches => {
      this.coaches = coaches;
    });
  }

  openDeleteModal(coachId: number): void {
    this.selectedCoachId = coachId;
    this.showModal = true;
  }

  closeDeleteModal(): void {
    this.selectedCoachId = null;
    this.showModal = false;
  }

  confirmDelete(): void {
    if (this.selectedCoachId !== null) {
      this.coachService.deleteCoach(this.selectedCoachId).subscribe({
        next: () => {
          this.loadCoaches(); // Recarrega a lista de técnicos após exclusão
          this.closeDeleteModal();
        },
        error: (error) => {
          console.error('Erro ao excluir o técnico', error);
          this.closeDeleteModal();
        }
      });
    }
  }
}
