import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class NoDirectAccessGuard implements CanActivate {

  constructor(private router: Router, private service: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const navigation = this.router.getCurrentNavigation();

    // Verifica se há navegação anterior ou se o usuário é admin
    if (navigation?.previousNavigation == null && !this.service.isAdmin()) {
      this.router.navigate(['']);
      return false;
    }

    return true;
  }
}
