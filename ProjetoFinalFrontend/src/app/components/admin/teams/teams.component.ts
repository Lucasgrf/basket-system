import { Component, OnInit } from '@angular/core';
import { Team } from '../../../models/team.model';
import { TeamService } from '../../../services/team.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [CommonModule, RouterLink, HeaderComponent],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.scss'
})
export class TeamsComponent implements OnInit {
  teams: Team[] = [];

  constructor(private teamService: TeamService) {}

  ngOnInit() {
    this.loadTeams();
  }

  loadTeams() {

  }

  deleteTeam(id: number) {

  }
}
