import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ArticleListComponent } from '../../article/components/article-list.component';
import { Profile } from '../models/profile.model';
import { ArticleListConfig } from '../../article/models/article-list-config.model';
import { ActivatedRoute } from '@angular/router';
import { ProfileService } from '../services/profile.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-profile-favorites',
  standalone: true,
  imports: [ArticleListComponent],
  template: `<app-article-list [limit]="10" [config]="favoriteConfig" />`,
})
export class ProfileFavoritesComponent implements OnInit {
  profile!: Profile;
  favoriteConfig!: ArticleListConfig;
  destroyRef = inject(DestroyRef);

  constructor(
    private route: ActivatedRoute,
    private readonly profileService: ProfileService
  ) {}

  ngOnInit() {
    this.profileService
      .get(this.route.parent?.snapshot.params['userName'])
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (profile: Profile) => {
          this.profile = profile;
          this.favoriteConfig = {
            type: 'all',
            filters: {
              favorited: this.profile.userName,
            },
          };
        },
      });
  }
}
