import { AsyncPipe, NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { IfAuthenticatedDirective } from '../auth/if-authenticated.directive';
import { UserService } from '../auth/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  standalone: true,
  imports: [
    RouterLinkActive,
    RouterLink,
    AsyncPipe,
    NgIf,
    IfAuthenticatedDirective,
  ],
})
export class HeaderComponent {
  currentUser$ = inject(UserService).currentUser;
}
