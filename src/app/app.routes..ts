import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { map } from 'rxjs';
import { UserService } from './core/auth/services/user.service';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/article/pages/home/home.component').then(
        m => m.HomeComponent
      ),
  },
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
  {
    path: 'profile',
    loadChildren: () => import('./features/profile/profile.route'),
  },
  {
    path: 'editor',
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/article/pages/editor/editor.component').then(
            m => m.EditorComponent
          ),
        canActivate: [() => inject(UserService).isAuthenticated],
      },
      {
        path: ':slug',
        loadComponent: () =>
          import('./features/article/pages/editor/editor.component').then(
            m => m.EditorComponent
          ),
        canActivate: [() => inject(UserService).isAuthenticated],
      },
    ],
  },
  {
    path: 'article/:slug',
    loadComponent: () =>
      import('./features/article/pages/article/article.component').then(
        m => m.ArticleComponent
      ),
  },
];
