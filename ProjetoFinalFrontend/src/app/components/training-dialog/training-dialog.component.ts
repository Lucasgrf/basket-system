import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Training } from '../../models/training.model';
import { FormsModule } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';

@Component({
  selector: 'app-training-dialog',
  standalone: true,
  imports: [FormsModule,MatFormField,MatLabel],
  templateUrl: './training-dialog.component.html',
  styleUrl: './training-dialog.component.scss'
})
export class TrainingDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TrainingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Training
  ) { }

  onSave(): void {
    this.dialogRef.close(this.data);
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
