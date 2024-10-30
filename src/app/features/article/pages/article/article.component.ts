import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ArticleMetaComponent } from '@/app/features/article/components/article-meta.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AsyncPipe, NgClass, NgForOf, NgIf } from '@angular/common';
import { FollowButtonComponent } from '@/app/features/profile/components/follow-button.component';
import { FavoriteButtonComponent } from '@/app/features/article/components/favorite-button.component';
import { MarkdownPipe } from '@/app/shared/pipes/markdown.pipe';
import { ListErrorsComponent } from '@/app/shared/component/list-errors/list-errors.component';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArticleCommentComponent } from '@/app/features/article/components/article-comment.component';
import { IfAuthenticatedDirective } from '@/app/core/auth/if-authenticated.directive';
import { ArticlesService } from '@/app/features/article/services/articles.service';
import { UserService } from '@/app/core/auth/services/user.service';
import { CommentService } from '@/app/features/article/services/comment.service';
import { Article } from '@/app/features/article/models/article.model';
import { User } from '@/app/core/auth/user.model';
import { Errors } from '@/app/core/models/errors.model';
import { catchError, combineLatest, throwError } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Profile } from '@/app/features/profile/models/profile.model';
import { Comment } from '@/app/features/article/models/comment.model';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    ArticleMetaComponent,
    RouterLink,
    NgClass,
    FollowButtonComponent,
    FavoriteButtonComponent,
    NgForOf,
    MarkdownPipe,
    AsyncPipe,
    ListErrorsComponent,
    FormsModule,
    ArticleCommentComponent,
    ReactiveFormsModule,
    IfAuthenticatedDirective,
    NgIf,
  ],
  templateUrl: './article.component.html',
})
export class ArticleComponent implements OnInit {
  article!: Article;
  currentUser!: User | null;
  comments: Comment[] = [];
  canModify: boolean = false;

  commentControl = new FormControl('', { nonNullable: true });
  commentFormErrors: Errors | null = null;

  isSubmitting = false;
  isDeleting = false;
  destroyRef = inject(DestroyRef);

  constructor(
    private readonly route: ActivatedRoute,
    private readonly articleService: ArticlesService,
    private readonly commentService: CommentService,
    private readonly router: Router,
    private readonly userService: UserService
  ) {}

  ngOnInit(): void {
    const slug = this.route.snapshot.params['slug'];
    combineLatest([
      this.articleService.get(slug),
      this.commentService.getAll(slug),
      this.userService.currentUser,
    ])
      .pipe(
        catchError(err => {
          void this.router.navigate(['/']);
          return throwError(() => err);
        }),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(([article, comments, currentUser]) => {
        this.article = article;
        this.comments = comments;
        this.currentUser = currentUser;
        this.canModify = currentUser?.userName === article.author.userName;
      });
  }

  onToggleFavorite(favorited: boolean): void {
    this.article.favorited = favorited;
    if (favorited) {
      this.article.favoritesCount++;
    } else {
      this.article.favoritesCount--;
    }
  }

  toggleFollowing(profile: Profile): void {
    this.article.author.following = profile.following;
  }

  deleteArticle(): void {
    this.isDeleting = true;
    this.articleService
      .delete(this.article.slug)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        void this.router.navigate(['/']);
      });
  }

  addComment() {
    this.isSubmitting = true;
    this.commentFormErrors = null;

    this.commentService
      .add(this.article.slug, this.commentControl.value)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: comment => {
          this.comments.unshift(comment);
          this.commentControl.reset('');
          this.isSubmitting = false;
        },
        error: errors => {
          this.isSubmitting = false;
          this.commentFormErrors = errors;
        },
      });
  }

  deleteComment(comment: Comment): void {
    this.commentService
      .delete(comment.id, this.article.slug)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.comments = this.comments.filter(item => item !== comment);
      });
  }
}
