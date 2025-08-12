import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterModule} from '@angular/router';
import { IUser } from '../../core/interface/user.interface';
import { AuthService } from '../../core/services/auth.service';
import { IProfile, ProfileService } from '../../core/services/profile.service';
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

    // tap(() => {
    // })
  }
  // users: IProfile = {};
  user: IUser = {
    name: 'Алексей Иванов',
    email: 'ivanov@example.com',
    avatarUrl: 'assets/avatar.jpg',
  };

  products: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.loadProducts();
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

