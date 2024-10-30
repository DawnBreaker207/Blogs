import { AsyncPipe, NgIf } from '@angular/common';
import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { catchError, combineLatest, of, switchMap, throwError } from 'rxjs';
import { UserService } from '../../../../core/auth/services/user.service';
import { FollowButtonComponent } from '../../components/follow-button.component';
import { Profile } from '../../models/profile.model';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    FollowButtonComponent,
    NgIf,
    RouterLink,
    AsyncPipe,
    RouterLinkActive,
    RouterOutlet,
  ],
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {
  profile!: Profile;
  isUser: boolean = false;
  destroyRef = inject(DestroyRef);
  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly userService: UserService,
    private readonly ProfileService: ProfileService
  ) {}
  ngOnInit(): void {
    this.ProfileService.get(this.route.snapshot.params['userName'])
      .pipe(
        catchError(error => {
          void this.router.navigate(['/']);
          return throwError(() => error);
        }),
        switchMap(profile => {
          return combineLatest([of(profile), this.userService.currentUser]);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(([profile, user]) => {
        this.profile = profile;
        this.isUser = profile.userName === user?.userName;
      });
  }

  onToggleFollowing(profile: Profile) {
    this.profile = profile;
  }
}
