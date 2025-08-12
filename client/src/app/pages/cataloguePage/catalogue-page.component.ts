import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {ProductService} from '../../core/services/product.service';

@Component({
  selector: 'app-catalogue-page',
  templateUrl: './catalogue-page.component.html',
  imports: [RouterLink, CommonModule],
  styleUrl: './catalogue-page.component.scss',
})
export class CataloguePageComponent {
  products: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error(err)
    });
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        this.products = this.products.filter(product => product.id !== id);
      },
      error: (err) => {
        console.error('Ошибка при удалении:', err);
      }
    });
  }
}
