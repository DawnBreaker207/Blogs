import { EMPTY } from 'rxjs';
import { JwtService } from './core/auth/services/jwt.service';
import { UserService } from './core/auth/services/user.service';
import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes.';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { apiInterceptor } from './core/auth/interceptors/api.interceptor';
import { tokenInterceptor } from './core/auth/interceptors/token.interceptor';
import { errorInterceptor } from './core/auth/interceptors/error.interceptor';
export function initAuth(jwtService: JwtService, userService: UserService) {
  return () => (jwtService.getToken() ? userService.getCurrentUser() : EMPTY);
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([apiInterceptor, tokenInterceptor, errorInterceptor])
    ),
    {
      provide: APP_INITIALIZER,
      useFactory: initAuth,
      deps: [JwtService, UserService],
      multi: true,
    },
  ],
};
