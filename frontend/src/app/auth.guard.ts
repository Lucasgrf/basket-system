import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "./services/auth.service";

export const RoleGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);
  const role = localStorage.getItem('role');

  // Verifica se o usuário está logado
  if (authService.isLogged()) {
    // Permissões baseadas no papel do usuário
    if (role === 'ADMIN') {
      // Admin tem acesso a todas as rotas
      return true;
    } else if (role === 'COACH') {
      // Coach tem acesso a rotas específicas
      if (['/players', '/team', '/trainings', '/dashboard', '/profile'].includes(state.url)) {
        return true;
      }
    } else if (role === 'PLAYER') {
      // Player tem acesso a rotas específicas
      if (['/team', '/dashboard', '/profile', '/trainings'].includes(state.url)) {
        return true;
      }
    }
  }

  // Se o usuário não estiver logado ou não tiver permissão, redireciona para a página 404
  router.navigate(['/404']);
  return false;
};
