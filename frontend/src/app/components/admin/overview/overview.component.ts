import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FooterComponent } from "../../footer/footer.component";
import { CommonModule } from '@angular/common';
import anime from 'animejs';
import { HeaderComponent } from '../header/header.component';
import { TeamService } from '../../../services/team.service';
import { TrainingService } from '../../../services/training.service';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [FooterComponent, CommonModule, HeaderComponent],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss'
})
export class OverviewComponent implements OnInit, AfterViewInit {
  isMenuOpen = false;
  totalUsers = 0;
  totalTeams = 0;
  totalTrainings = 0;

  constructor(
    private userService: UserService,
    private teamService: TeamService,
    private trainingService: TrainingService
  ) { }

  ngOnInit(): void {
    this.fetchData();
  }

  ngAfterViewInit(): void {
    this.animateCards();
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  private animateCards(): void {
    anime({
      targets: '.card',
      opacity: [0, 1],
      translateY: [50, 0],
      easing: 'easeOutQuad',
      duration: 800,
      delay: anime.stagger(100) // Stagger animation delay for each card
    });
  }

  private fetchData(): void {
    this.userService.getAllUsers().subscribe(users => {
      this.totalUsers = users.length;
    });

    this.teamService.getAllTeams().subscribe(teams => {
      this.totalTeams = teams.length;
    });

    this.trainingService.getAllTrainings().subscribe(trainings => {
      this.totalTrainings = trainings.length;
    });
  }
}
