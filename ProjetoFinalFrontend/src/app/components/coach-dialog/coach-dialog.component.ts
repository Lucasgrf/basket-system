import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Team } from '../../models/team.model';
import { CoachService } from '../../services/coach.service';
import { TeamService } from '../../services/team.service';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';

@Component({
  selector: 'app-coach-dialog',
  standalone: true,
  imports: [MatFormField,MatLabel, MatSelect,MatOption, ReactiveFormsModule],
  templateUrl: './coach-dialog.component.html',
  styleUrl: './coach-dialog.component.scss'
})
export class CoachDialogComponent {
  coachForm!: FormGroup;
  teams: Team[] = [];

  constructor(
    private fb: FormBuilder,
    private coachService: CoachService,
    private teamService: TeamService,
    private dialogRef: MatDialogRef<CoachDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {
    this.initializeForm();
    this.loadTeams();
  }

  initializeForm() {
    this.coachForm = this.fb.group({
      name: [this.data.coach?.name || '', Validators.required],
      email: [this.data.coach?.email || '', [Validators.required, Validators.email]],
      photoUrl: [this.data.coach?.photoUrl || '', Validators.required],
      teamId: [this.data.coach?.team?.id || '', Validators.required]
    });
  }

  loadTeams() {

  }

  onSubmit() {

  }

  onCancel() {
    this.dialogRef.close();
  }
}
