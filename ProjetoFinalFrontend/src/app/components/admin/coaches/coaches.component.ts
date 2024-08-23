import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Coach } from '../../../models/coach.model';
import { CoachService } from '../../../services/coach.service';
import { CoachDialogComponent } from '../../coach-dialog/coach-dialog.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-coaches',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './coaches.component.html',
  styleUrl: './coaches.component.scss'
})
export class CoachesComponent implements OnInit {
  coaches: Coach[] = [];

  constructor(private coachService: CoachService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadCoaches();
  }

  loadCoaches() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(CoachDialogComponent, {
      width: '600px',
      data: { action: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadCoaches();
      }
    });
  }

  openEditDialog(coach: Coach) {
    const dialogRef = this.dialog.open(CoachDialogComponent, {
      width: '600px',
      data: { action: 'edit', coach }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadCoaches();
      }
    });
  }

  deleteCoach(id: number) {

  }
}
