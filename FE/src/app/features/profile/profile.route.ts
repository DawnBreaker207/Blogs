import { Routes } from '@angular/router';
import { ProfileComponent } from './pages/profile/profile.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: ':username',
        component: ProfileComponent,
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./components/profile-articles.component').then(
                m => m.ProfileArticlesComponent
              ),
          },
          {
            path: 'favorites',
            loadComponent: () =>
              import('./components/profile-favorites.component').then(
                m => m.ProfileFavoritesComponent
              ),
          },
        ],
      },
    ],
  },
];
export default routes;
