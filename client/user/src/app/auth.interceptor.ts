import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private injector: Injector, private authService: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let modifiedRequest = request.clone({
      setHeaders: {
        'Content-Type': 'application/json; charset=utf-8',
        Authorization: `rentx ${localStorage.getItem('token')}`,
      },
    });

    return next.handle(modifiedRequest).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 403 || err.status === 401) {
          return this.handle401Error(request, next);
        } else {
          return throwError(() => err);
        }
      })
    );
  }

  private handle401Error(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return this.authService.refreshToken().pipe(
      switchMap((data: any) => {
        this.authService.newToken(
          data.accessToken.value,
          data.refreshToken.value
        );

        return next.handle(
          request.clone({
            setHeaders: {
              Authorization: `rentx ${data.accessToken.value}`,
            },
          })
        );
      }),
      catchError((errodata) => {
        this.authService.logOut();

        return throwError(() => errodata);
      })
    );
  }
}
