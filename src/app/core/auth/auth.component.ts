import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from './services/user.service';
import { Errors } from '../models/errors.model';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NgIf } from '@angular/common';
import { ListErrorsComponent } from '../../shared/component/list-errors/list-errors.component';
interface AuthForm {
  email: FormControl<string>;
  password: FormControl<string>;
  userName?: FormControl<string>;
}
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  standalone: true,
  imports: [RouterLink, NgIf, ListErrorsComponent, ReactiveFormsModule],
})
export class AuthComponent implements OnInit {
  authType = '';
  title = '';
  errors: Errors = { errors: {} };
  isSubmitting = false;
  authForm: FormGroup<AuthForm>;
  destroyRef = inject(DestroyRef);
  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly userService: UserService
  ) {
    this.authForm = new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
      password: new FormControl('', {
        validators: [Validators.required],
        nonNullable: true,
      }),
    });
  }

  ngOnInit(): void {
    this.authType = this.route.snapshot.url.at(-1)!.path;
    this.title = this.authType === 'login' ? 'Sign In' : 'Sign Up';
    if (this.authType === 'register') {
      this.authForm.addControl(
        'userName',
        new FormControl('', {
          validators: [Validators.required],
          nonNullable: true,
        })
      );
    }
  }

  submitForm(): void {
    this.isSubmitting = true;
    this.errors = { errors: {} };

    const observable =
      this.authType === 'login'
        ? this.userService.login(
            this.authForm.value as { email: string; password: string }
          )
        : this.userService.register(
            this.authForm.value as {
              userName: string;
              email: string;
              password: string;
            }
          );
    observable.pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: () => void this.router.navigate(['/']),
      error: err => {
        this.errors = err;
        this.isSubmitting = false;
      },
    });
  }
}
