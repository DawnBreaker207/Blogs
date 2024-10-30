import { UserService } from '@/app/core/auth/services/user.service';
import { AsyncPipe, DatePipe, NgIf } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { map } from 'rxjs';
import { User } from '../../../core/auth/user.model';
import { Comment } from '../models/comment.model';

@Component({
  selector: 'app-article-comment',
  standalone: true,
  imports: [RouterLink, DatePipe, NgIf, AsyncPipe],
  template: `
    @if (comment) {
      <div class="card">
        <div class="card-block">
          <p class="card-text">
            {{ comment.body }}
          </p>
        </div>
        <div class="card-footer">
          <a
            class="comment-author"
            [routerLink]="['/profile', comment.author.userName]">
            <img
              [src]="comment.author.image"
              class="comment-author-img"
              [alt]="" />
          </a>
          &nbsp;
          <a
            class="comment-author"
            [routerLink]="['/profile', comment.author.userName]">
            {{ comment.author.userName }}
          </a>
          <span class="date-posted">
            {{ comment.createdAt | date: 'longDate' }}
          </span>
          @if (canModify$ | async) {
            <span class="mod-options">
              <i
                class="ion-trash-a"
                tabindex="0"
                (click)="delete.emit(true)"
                (keydown.enter)="delete.emit(true)"
                (keydown.space)="delete.emit(true)"></i>
            </span>
          }
        </div>
      </div>
    }
  `,
})
export class ArticleCommentComponent {
  @Input() comment!: Comment;
  @Output() delete = new EventEmitter<boolean>();

  canModify$ = inject(UserService).currentUser.pipe(
    map(
      (userData: User | null) =>
        userData?.userName === this.comment.author.userName
    )
  );
}
