import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthFormComponent } from '../auth-form/auth-form.component';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import {MailService} from '../../core/services/mail.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, AuthFormComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  router = inject(Router);
  isReg: boolean = false;

  authService = inject(AuthService);
  emailService = inject(MailService)
  form = new FormGroup({
    usernameOrEmail: new FormControl<string | null>(null, [
      Validators.required,
    ]),
    password: new FormControl<string | null>(null, [
      Validators.required,
      Validators.minLength(6),
    ]),
  });

  onSubmit = () => {
    console.log('usernameOrEmail:', this.form.get('usernameOrEmail')?.value);
    console.log('password:', this.form.get('password')?.value);
    console.log('form valid:', this.form.valid);

    if (this.form.valid) {
      //@ts-ignore
      this.authService.login(this.form.value).subscribe({
        next: (res) => {
          console.log('Успешный вход, ответ:', res);
          this.router.navigate(['/profile']);
        },
        error: (err) => {
          console.log('Ошибка авторизации:', err);
        }
      });
    } else {
      console.log('Не валидный логин или пароль');
    }
  };

}
