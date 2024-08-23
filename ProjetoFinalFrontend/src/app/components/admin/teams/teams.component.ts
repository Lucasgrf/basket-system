import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Team } from '../../../models/team.model';
import { TeamDialogComponent } from '../../team-dialog/team-dialog.component';
import { TeamService } from '../../../services/team.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.scss'
})
export class TeamsComponent implements OnInit {
  teams: Team[] = [];

  constructor(private teamService: TeamService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadTeams();
  }

  loadTeams() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(TeamDialogComponent, {
      width: '600px',
      data: { action: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadTeams();
      }
    });
  }

  openEditDialog(team: Team) {
    const dialogRef = this.dialog.open(TeamDialogComponent, {
      width: '600px',
      data: { action: 'edit', team }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadTeams();
      }
    });
  }

  deleteTeam(id: number) {

  }
}
