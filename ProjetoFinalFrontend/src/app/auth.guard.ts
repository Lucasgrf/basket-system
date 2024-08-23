import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "./services/auth.service";

export const authorizationGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);
  return router.navigate(['auth/login']);
}

export const RoleGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);
  const role = localStorage.getItem('role');

  if (authService.isLogged) {
    if (role === 'ADMIN') {
      // Admin has access to all routes
      return true;
    } else if (role === 'COACH') {
      // Coach has access to specific routes
      if (['/players', '/team', '/trainings', '/dashboard', '/profile'].includes(state.url)) {
        return true;
      } else {
        return router.navigate(['/dashboard']);
      }
    } else if (role === 'PLAYER') {
      // Player has access to specific routes
      if (['/team', '/dashboard', '/profile', '/trainings'].includes(state.url)) {
        return true;
      } else {
        return router.navigate(['/dashboard']);
      }
    }
  }

  // If the user is not logged in or the role doesn't match, redirect to login
  return router.navigate(['auth/login']);
};

