import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtService } from './jwt.service';
import { Router } from '@angular/router';
import { User } from '../user.model';
import {
  BehaviorSubject,
  distinctUntilChanged,
  map,
  Observable,
  tap,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser = this.currentUserSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  public isAuthenticated = this.currentUser.pipe(map(user => !!user));

  constructor(
    private readonly http: HttpClient,
    private readonly jwtService: JwtService,
    private readonly router: Router
  ) {}

  login(credentials: {
    email: string;
    password: string;
  }): Observable<{ user: User }> {
    return this.http
      .post<{ user: User }>('/users/login', { user: credentials })
      .pipe(tap(({ user }) => this.setAuth(user)));
  }

  register(credentials: {
    userName: string;
    email: string;
    password: string;
  }): Observable<{ user: User }> {
    return this.http
      .post<{ user: User }>('/users', { user: credentials })
      .pipe(tap(({ user }) => this.setAuth(user)));
  }

  logout(): void {
    this.purgeAuth();
    void this.router.navigate(['/']);
  }

  getCurrentUser(): Observable<{ user: User }> {
    return this.http.get<{ user: User }>('/user').pipe(
      tap({
        next: ({ user }) => this.setAuth(user),
        error: () => this.purgeAuth(),
      })
    );
  }

  update(user: Partial<User>): Observable<{ user: User }> {
    return this.http
      .put<{ user: User }>('/user', { user })
      .pipe(tap(({ user }) => this.currentUserSubject.next(user)));
  }

  setAuth(user: User): void {
    this.jwtService.saveToken(user.token);
    this.currentUserSubject.next(user);
  }

  purgeAuth(): void {
    this.jwtService.destroyToken();
    this.currentUserSubject.next(null);
  }
}
