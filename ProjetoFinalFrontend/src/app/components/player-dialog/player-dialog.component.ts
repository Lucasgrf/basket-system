import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Team } from '../../models/team.model';
import { PlayerService } from '../../services/player.service';
import { TeamService } from '../../services/team.service';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatOption } from '@angular/material/core';

@Component({
  selector: 'app-player-dialog',
  standalone: true,
  imports: [MatFormField, MatLabel, ReactiveFormsModule,MatOption],
  templateUrl: './player-dialog.component.html',
  styleUrl: './player-dialog.component.scss'
})
export class PlayerDialogComponent implements OnInit {
  playerForm!: FormGroup;
  teams: Team[] = [];

  constructor(
    private fb: FormBuilder,
    private playerService: PlayerService,
    private teamService: TeamService,
    private dialogRef: MatDialogRef<PlayerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {
    this.initializeForm();
    this.loadTeams();
  }

  initializeForm() {
    this.playerForm = this.fb.group({
      name: [this.data.player?.name || '', Validators.required],
      email: [this.data.player?.email || '', [Validators.required, Validators.email]],
      photoUrl: [this.data.player?.photoUrl || '', Validators.required],
      teamId: [this.data.player?.team?.id || '', Validators.required]
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
