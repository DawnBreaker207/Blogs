import { NgClass } from '@angular/common';
import {
  Component,
  DestroyRef,
  EventEmitter,
  inject,
  Input,
  Output,
} from '@angular/core';
import { Profile } from '../models/profile.model';
import { ProfileService } from '../services/profile.service';
import { Router } from '@angular/router';
import { UserService } from '../../../core/auth/services/user.service';
import { EMPTY, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-follow-button',
  standalone: true,
  imports: [NgClass],
  template: `<button
    class="btn btn-sm action-btn"
    [ngClass]="{
      disabled: isSubmitting,
      'btn-outlint-secondary': !profile.following,
      'btn-sencondary': profile.following,
    }"
    (click)="(toggleFollowing)">
    <i class="ion-plus-round"></i>
    &nbsp;
    {{ profile.following ? 'Unfollow' : 'Follow' }} {{ profile.userName }}
  </button>`,
})
export class FollowButtonComponent {
  @Input() profile!: Profile;
  @Output() toggle = new EventEmitter<Profile>();
  isSubmitting = false;
  destroyRef = inject(DestroyRef);

  constructor(
    private readonly profileService: ProfileService,
    private readonly router: Router,
    private readonly userService: UserService
  ) {}

  toggleFollowing(): void {
    this.isSubmitting = true;
    this.userService.isAuthenticated
      .pipe(
        switchMap((isAuthenticated: boolean) => {
          if (!isAuthenticated) {
            void this.router.navigate(['/login']);
            return EMPTY;
          }

          if (!this.profile.following) {
            return this.profileService.follow(this.profile.userName);
          } else {
            return this.profileService.unFollow(this.profile.userName);
          }
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: profile => {
          this.isSubmitting = false;
          this.toggle.emit(profile);
        },
        error: () => (this.isSubmitting = false),
      });
  }
}
