import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterModule} from '@angular/router';
import { IUser } from '../../core/interface/user.interface';
import { AuthService } from '../../core/services/auth.service';
import { ProfileService } from '../../core/services/profile.service';
import {ProductService} from '../../core/services/product.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  authService = inject(AuthService);
  profileService = inject(ProfileService);
  router = inject(Router);
  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  products: any[] = [];

  user: IUser | null = null;
  isLoading = true;
  error: string | null = null;

  constructor(private productService: ProductService, profileService: ProfileService) {}

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
    this.loadProducts();
  }

  loadUser(){
    this.profileService.getMe()
  }

  loadProducts() {
    this.productService.getUserProducts().subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error(err)
    });
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        this.products = this.products.filter(productDto => productDto.id !== id);
      },
      error: (err) => {
        console.error('Ошибка при удалении:', err);
      }
    });
  }
}

