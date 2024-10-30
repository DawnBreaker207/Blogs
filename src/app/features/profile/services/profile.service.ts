import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, shareReplay } from 'rxjs';
import { Profile } from '../models/profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  constructor(private readonly http: HttpClient) {}

  get(userName: string): Observable<Profile> {
    return this.http.get<{ profile: Profile }>('/profile/' + userName).pipe(
      map((data: { profile: Profile }) => data.profile),
      shareReplay(1)
    );
  }
  follow(userName: string): Observable<Profile> {
    return this.http
      .post<{ profile: Profile }>('/profiles/' + userName + '/follow', {})
      .pipe(map((data: { profile: Profile }) => data.profile));
  }
  unFollow(userName: string): Observable<Profile> {
    return this.http
      .delete<{ profile: Profile }>('/profiles/' + userName + '/follow')
      .pipe(map((data: { profile: Profile }) => data.profile));
  }
}
