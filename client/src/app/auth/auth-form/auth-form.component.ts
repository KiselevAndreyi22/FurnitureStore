import {Component, inject, Input} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MailService} from '../../core/services/mail.service';
import {UpdatePasswordRequest} from '../../core/interface/update-password';
import {RecoveryService} from '../../core/services/recovery.service';
import {AuthService} from '../../core/services/auth.service';

@Component({
  selector: 'app-auth-form',
  imports: [ReactiveFormsModule],
  templateUrl: './auth-form.component.html',
  styleUrl: './auth-form.component.scss',
})
export class AuthFormComponent {
  authService = inject(AuthService);
  @Input() isReg: boolean = false;
  @Input() onSubmit!: () => void;
  @Input() form!: FormGroup;
  isRestoreMode = false;
  isCodeConfirmMode = false;
  recoveryEmail: string = '';

  switchToReg(event: Event) {
    event.preventDefault();
    this.isReg = true;
    this.isRestoreMode = false;
    this.form.reset();
  }

  switchToLogin(event: Event) {
    event.preventDefault();
    this.isReg = false;
    this.isRestoreMode = false;
    this.form.reset();
  }

  switchToRestore(event: Event) {
    event.preventDefault();
    this.isRestoreMode = true;
    this.isReg = false;
    this.form = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email])
    });
  }

  backToLogin(event: Event) {
    event.preventDefault();
    this.isRestoreMode = false;
    this.isReg = false;
    this.form.reset();
    window.location.href = '/login'; //costyl
  }

  switchToCodeConfirm() {
    this.isRestoreMode = false;
    this.isReg = false;
    this.isCodeConfirmMode = true;
    this.form = new FormGroup({
      code: new FormControl('', Validators.required),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(6)])
    });
  }

  router = inject(Router);
  mailService = inject(MailService);
  recoveryService = inject(RecoveryService);

  sendCode() {
    const email = this.form.get('email')?.value;
    if (email) {
      this.mailService.sendVerificationEmail(email).subscribe((res) => {
        this.recoveryEmail = email; // <--- сохраняем email!
        this.switchToCodeConfirm();
      });
    }
  }

  code: string = '';
  password: string = '';
  message: string = '';

  confirmCodeAndUpdatePassword() {
    const code = this.form.get('code')?.value;
    const password = this.form.get('newPassword')?.value;
    if (!code || !password) {
      this.message = 'Пожалуйста, заполните все поля';
      return;
    }
    const request: UpdatePasswordRequest = {
      email: this.recoveryEmail,
      code,
      password
    };
    this.recoveryService.updatePassword(request).subscribe({
      next: (response) => {
        this.message = response;
      },
      error: () => this.message = 'Ошибка при смене пароля!'
    });
    this.authService.logout();
    window.location.href = '/login'; //costyl
  }

  /*onSubmit() {
    if (this.isRestoreMode) {
      // Логика отправки кода на email
      const email = this.form.value.email;
      // ...
    } else if (this.isReg) {
      // Логика регистрации
    } else {
      // Логика логина
    }
  }*/
}
