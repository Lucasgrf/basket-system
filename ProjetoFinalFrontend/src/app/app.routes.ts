import { Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component'; // Import the LoginComponent from the correct file path
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { AboutPageComponent } from './components/about-page/about-page.component';
import { InitialPageComponent } from './components/initial-page/initial-page.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { HomePageComponent } from './components/home-page/home-page.component';

export const routes: Routes = [
  {
    path: 'login',component: LoginPageComponent
  },
  {
    path: 'register', component: RegisterPageComponent
  },
  {
    path: 'about', component: AboutPageComponent
  },
  {
    path: 'initial', component: InitialPageComponent
  },
  {
    path: 'profile', component: ProfilePageComponent
  },
  {
    path: '', component: HomePageComponent
  }

];
