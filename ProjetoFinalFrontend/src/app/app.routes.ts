import { Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { AboutPageComponent } from './components/about-page/about-page.component';
import { InitialPageComponent } from './components/initial-page/initial-page.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { PlayerPageComponent } from './components/player-page/player-page.component';
import { TeamPageComponent } from './components/team-page/team-page.component';
import { RoleGuard } from './auth.guard';
import { TrainingPageComponent } from './components/training-page/training-page.component';
import { HomeComponent } from './components/admin/home/home.component';
import { UsersComponent } from './components/admin/users/users.component';
import { TeamsComponent } from './components/admin/teams/teams.component';
import { TrainingsComponent } from './components/admin/trainings/trainings.component';
import { FormAddUserComponent } from './components/admin/forms/form-add-user/form-add-user.component';
import { FormUpdateUserComponent } from './components/admin/forms/form-update-user/form-update-user.component';
import { FormAddTeamComponent } from './components/admin/forms/form-add-team/form-add-team.component';
import { FormUpdateTeamComponent } from './components/admin/forms/form-update-team/form-update-team.component';
import { FormAddTrainingComponent } from './components/admin/forms/form-add-training/form-add-training.component';
import { FormUpdateTrainingComponent } from './components/admin/forms/form-update-training/form-update-training.component';
import { PlayersComponent } from './components/admin/players/players.component';
import { FormAddPlayerComponent } from './components/admin/forms/form-add-player/form-add-player.component';
import { FormUpdatePlayerComponent } from './components/admin/forms/form-update-player/form-update-player.component';
import { CoachesComponent } from './components/admin/coaches/coaches.component';
import { FormAddCoachComponent } from './components/admin/forms/form-add-coach/form-add-coach.component';
import { FormUpdateCoachComponent } from './components/admin/forms/form-update-coach/form-update-coach.component';
import { OverviewComponent } from './components/admin/overview/overview.component';
import { CredentialsComponent } from './components/admin/credentials/credentials.component';
import { FormUpdateCredentialComponent } from './components/admin/forms/form-update-credential/form-update-credential.component';
import { NoDirectAccessGuard } from './NoDirectAccessGuard';
import { CoachPageComponent } from './components/coach-page/coach-page.component';

export const routes: Routes = [
  { path: 'admin', component: HomeComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/users', component: UsersComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/teams', component: TeamsComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/trainings', component: TrainingsComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/users/add', component: FormAddUserComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/users/edit/:id', component: FormUpdateUserComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/teams/add', component: FormAddTeamComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/teams/edit/:id', component: FormUpdateTeamComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/trainings/add', component: FormAddTrainingComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/trainings/edit/:id', component: FormUpdateTrainingComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/players', component: PlayersComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/players/add', component: FormAddPlayerComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/players/edit/:id', component: FormUpdatePlayerComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/coaches', component: CoachesComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/coaches/add', component: FormAddCoachComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/coaches/edit/:id', component: FormUpdateCoachComponent, canActivate: [NoDirectAccessGuard] },
  { path: 'admin/overview', component: OverviewComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/users/credentials', component: CredentialsComponent, canActivate: [NoDirectAccessGuard]},
  { path: 'admin/credentials/edit/:id', component: FormUpdateCredentialComponent, canActivate: [NoDirectAccessGuard]},
  {
    path: '',
    component: HomePageComponent
  },
  {
    path: 'home',
    component: HomePageComponent
  },
  {
    path: 'about',
    component: AboutPageComponent
  },
  {
    path: 'contact',
    component: ContactUsComponent
  },
  {
    path: 'auth',
    component: AuthenticationComponent,
    children: [
      {
        path: 'login',
        component: LoginPageComponent
      },
      {
        path: 'register',
        component: RegisterPageComponent
      }
    ]
  },
  {
    path: 'dashboard',
    component: InitialPageComponent
  },
  {
    path: 'profile',
    component: ProfilePageComponent,
    canActivate: [NoDirectAccessGuard]
  },
  {
    path: 'players',
    component: PlayerPageComponent,
    canActivate: [NoDirectAccessGuard]
  },
  {
    path: 'coaches',
    component: CoachPageComponent,
    canActivate: [NoDirectAccessGuard]
  },
  {
    path: 'teams',
    component: TeamPageComponent,
    canActivate: [NoDirectAccessGuard]
  },
  {
    path: 'trainings',
    component: TrainingPageComponent,
    canActivate: [NoDirectAccessGuard]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
