import {Component, inject} from '@angular/core'
import { ProfileService} from '../../core/services/profile.service';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {IUser} from '../../core/interface/user.interface';
import {AuthService} from '../../core/services/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'profile-options',
  templateUrl: 'profile-options.component.html',
  imports: [
    FormsModule,
    NgIf
  ],
  styleUrls: ['./profile-options.component.scss']
})
export class OptionsFormComponent{

  authService = inject(AuthService);
  profileService = inject(ProfileService);
  router = inject(Router);
  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  defaultAvatarUrl = 'http://localhost:8080/uploads/products/default.png';
  selectedFile: File | null = null;
  user: IUser | null = null;
  isLoading = true;
  error: string | null = null;

  ngOnInit() {
    this.profileService.getMe().subscribe({
      next: (data) => {
        this.user = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Ошибка загрузки профиля!';
        this.isLoading = false;
      }
    });
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.selectedFile) {
      this.profileService.updateAvatar(this.selectedFile).subscribe({
        next: (response) => {
          console.log('Аватар обновлен:', response);
        },
        error: (err) => {
          console.error('Ошибка при обновлении аватара:', err);
        }
      });
    } else {
      alert('Не выбран файл!');
    }
  }

}
