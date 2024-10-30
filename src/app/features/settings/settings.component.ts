import { UserService } from '@/app/core/auth/services/user.service';
import { User } from '@/app/core/auth/user.model';
import { Errors } from '@/app/core/models/errors.model';
import { ListErrorsComponent } from '@/app/shared/component/list-errors/list-errors.component';
import { Component, OnInit, DestroyRef, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
interface SettingForm {
  image: FormControl<string>;
  userName: FormControl<string>;
  bio: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
}
@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [ListErrorsComponent, ReactiveFormsModule],
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  user!: User;
  settingsForm = new FormGroup<SettingForm>({
    image: new FormControl('', { nonNullable: true }),
    userName: new FormControl('', { nonNullable: true }),
    bio: new FormControl('', { nonNullable: true }),
    email: new FormControl('', { nonNullable: true }),
    password: new FormControl('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });
  errors: Errors | null = null;
  isSubmitting = false;
  destroyRef = inject(DestroyRef);
  constructor(
    private readonly router: Router,
    private readonly userService: UserService
  ) {}

  ngOnInit(): void {
    this.settingsForm.patchValue(
      this.userService.getCurrentUser() as Partial<User>
    );
  }
  logout(): void {
    this.userService.logout();
  }

  submitForm() {
    this.isSubmitting = true;

    this.userService.update(this.settingsForm.value).subscribe({
      next: ({ user }) =>
        void this.router.navigate(['/profile/', user.username]),

      error: err => {
        this.errors = err;
        this.isSubmitting = false;
      },
    });
  }
}
