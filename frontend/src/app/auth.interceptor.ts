import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Obter o token de onde estiver armazenado, por exemplo, localStorage
    const authToken = localStorage.getItem('JWT');

    // Clonar a requisição e adicionar o cabeçalho de autorização se o token existir
    if (authToken) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`
        }
      });
      return next.handle(authReq);
    } else {
      // Continuar com a requisição original se não houver token
      return next.handle(req);
    }
  }
}
