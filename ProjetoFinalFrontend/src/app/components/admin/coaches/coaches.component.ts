import { Component} from '@angular/core';
import { Coach } from '../../../models/coach.model';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../header/header.component";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-coaches',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterLink],
  templateUrl: './coaches.component.html',
  styleUrl: './coaches.component.scss'
})
export class CoachesComponent {
  coaches: Coach[] = [];

  constructor() {}

  ngOnInit() {
  }

  loadCoaches() {

  }

  deleteCoach(id: number) {

  }
}
