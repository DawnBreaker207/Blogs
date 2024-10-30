import { DatePipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Article } from '../models/article.model';

@Component({
  selector: 'app-article-meta',
  standalone: true,
  imports: [RouterLink, DatePipe],
  template: `
    <div class="article-meta">
      <a [routerLink]="['/profile', article.author.userName]">
        <img [src]="article.author.image" [alt]="" />
      </a>

      <div class="info">
        <a class="author" [routerLink]="['/profile', article.author.userName]">
          {{ article.author.userName }}
        </a>
        <span class="date">
          {{ article.createdAt | date: 'longDate' }}
        </span>
      </div>

      <ng-content></ng-content>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ArticleMetaComponent {
  @Input() article!: Article;
}
