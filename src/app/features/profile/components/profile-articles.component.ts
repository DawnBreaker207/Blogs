import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ArticleListComponent } from '../../article/components/article-list.component';
import { Profile } from '../models/profile.model';
import { ArticleListConfig } from '../../article/models/article-list-config.model';
import { ActivatedRoute } from '@angular/router';
import { ProfileService } from '../services/profile.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-profile-articles',
  standalone: true,
  imports: [ArticleListComponent],
  template: `<app-article-list [limit]="10" [config]="articleConfig" ]] />`,
})
export class ProfileArticlesComponent implements OnInit {
  profile!: Profile;
  articleConfig!: ArticleListConfig;
  destroyRef = inject(DestroyRef);

  constructor(
    private route: ActivatedRoute,
    private readonly profileService: ProfileService
  ) {}
  ngOnInit(): void {
    this.profileService
      .get(this.route.snapshot.params['userName'])
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (profile: Profile) => {
          this.profile = profile;
          this.articleConfig = {
            type: 'all',
            filters: { author: this.profile.userName },
          };
        },
        error: err => console.error('Error fetching profile:', err),
      });
  }
}
