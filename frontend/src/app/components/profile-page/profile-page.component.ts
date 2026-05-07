import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HeaderComponent } from "../header/header.component";
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { TeamService } from '../../services/team.service';
import { PlayerService } from '../../services/player.service';
import { CoachService } from '../../services/coach.service';
import { Player } from '../../models/player.model';
import { Coach } from '../../models/coach.model';
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HeaderComponent],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.scss'
})
export class ProfilePageComponent implements OnInit {
  profileForm!: FormGroup;
  user!: User;
  userId = parseInt(localStorage.getItem('userId') || '0');
  role = localStorage.getItem('role');
  isEditing: boolean = false;
  isCredentialVisible: boolean = false;
  team: any;
  coach!: Coach;
  player!: Player;
  photo: string = 'https://static.vecteezy.com/system/resources/previews/018/765/757/original/user-profile-icon-in-flat-style-member-avatar-illustration-on-isolated-background-human-permission-sign-business-concept-vector.jpg';

  constructor(private fb: FormBuilder,
    private service: UserService,
    private route: Router,
    private coachService: CoachService,
    private playerService: PlayerService,
    private teamService: TeamService) {
    {
      this.profileForm = this.fb.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['',],
      });
    }
  }

  ngOnInit(): void {
    this.loadUser(this.userId);
  }

  saveProfile() {
    if (this.profileForm.valid) {
      this.service.updateProfile(this.userId, this.profileForm.value).subscribe({
        next: () => {
          alert('Perfil atualizado com sucesso');
          this.route.navigate(['/dashboard']);
        },
        error: (error) => {
          alert('Erro ao atualizar perfil' + error);
        }
      });
    } else {
      console.log('Formulário inválido');
    }
  }

  loadUser(userId?: number): void {
    // Fetch user data and patch the form
    this.service.getUserById(this.userId).subscribe(user => {
      this.user = user;
      this.profileForm.patchValue({ username: user.username, email: user.email });
    });
    if(this.role === 'PLAYER') {
      this.playerService.getPlayerByUserId(this.userId).subscribe(player => {
        this.player = player;
        this.teamService.getTeamById(player.teamId).subscribe(team => {
          this.team = team.name;
        });
      });
    } else if(this.role === 'COACH') {
      this.coachService.getCoachByUserId(this.userId).subscribe(coach => {
        this.coach = coach;
        this.teamService.getTeamById(coach.teamId).subscribe(team => {
          this.team = team.name;
        });
      });
    }
  }

  editProfile() {
    this.isEditing = true;
  }

  uploadPhoto() {
    const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
    fileInput?.click();
  }

  handleFileInput(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      console.log(file);
      // Implement the upload functionality here
    }
  }

  toggleCredential() {
    this.isCredentialVisible = !this.isCredentialVisible;
  }
}
