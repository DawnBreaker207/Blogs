import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { AsyncPipe, NgClass, NgForOf } from '@angular/common';
import { ArticleListComponent } from '@/app/features/article/components/article-list.component';
import { IfAuthenticatedDirective } from '@/app/core/auth/if-authenticated.directive';
import { RxLet } from '@rx-angular/template/let';
import { UserService } from '@/app/core/auth/services/user.service';
import { Router } from '@angular/router';
import { ArticleListConfig } from '@/app/features/article/models/article-list-config.model';
import { TagsService } from '@/app/features/article/services/tags.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgClass,
    ArticleListComponent,
    AsyncPipe,
    NgForOf,
    IfAuthenticatedDirective,
    RxLet,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  isAuthenticated = false;
  listConfig: ArticleListConfig = {
    type: 'all',
    filters: {},
  };
  tags$ = inject(TagsService)
    .getAll()
    .pipe(tap(() => (this.tagsLoaded = true)));
  tagsLoaded = false;
  destroyRef = inject(DestroyRef);

  constructor(
    private readonly router: Router,
    private readonly userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.isAuthenticated.pipe();
  }

  setListTo(type: string = '', filters: object = {}): void {
    // If feed is requested but user is not authenticated, redirect to log in
    if (type === 'feed' && !this.isAuthenticated) {
      void this.router.navigate(['/login']);
      return;
    }
    //   Otherwise, update the list object
    this.listConfig = { type: type, filters: filters };
  }
}
