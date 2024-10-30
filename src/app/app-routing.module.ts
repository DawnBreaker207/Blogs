import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { map } from 'rxjs';
import { UserService } from './core/auth/services/user.service';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./core/auth/auth.component').then(m => m.AuthComponent),
    canActivate: [
      () => inject(UserService).isAuthenticated.pipe(map(isAuth => !isAuth)),
    ],
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./core/auth/auth.component').then(m => m.AuthComponent),
    canActivate: [
      () => inject(UserService).isAuthenticated.pipe(map(isAuth => !isAuth)),
    ],
  },
  {
    path: 'settings',
    loadComponent: () =>
      import('./features/settings/settings.component').then(
        m => m.SettingsComponent
      ),
    canActivate: [() => inject(UserService).isAuthenticated],
  },
];
