import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Team } from '../../models/team.model';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-team-dialog',
  standalone: true,
  imports: [MatFormField,MatLabel,FormsModule],
  templateUrl: './team-dialog.component.html',
  styleUrl: './team-dialog.component.scss'
})
export class TeamDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TeamDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Team
  ) { }

  onSave(): void {
    this.dialogRef.close(this.data);
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
