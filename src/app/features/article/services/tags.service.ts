import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TagsService {
  constructor(private readonly http: HttpClient) {}
  getAll(): Observable<string[]> {
    return this.http
      .get<{ tags: string[] }>('/tags')
      .pipe(map(data => data.tags));
  }
}
