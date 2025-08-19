import {CommonModule} from '@angular/common';
import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ProductService} from '../../core/services/product.service';
import {ProductCategory} from '../../core/enum/product-category';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-catalogue-page',
  templateUrl: './catalogue-page.component.html',
  imports: [RouterLink, CommonModule, FormsModule],
  styleUrl: './catalogue-page.component.scss',
})
export class CataloguePageComponent {
  products: any[] = [];
  categories = Object.values(ProductCategory);
  selectedCategory = ProductCategory.ALL;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.loadAllProducts();
  }

  onCategoryChange(category: ProductCategory) {
    if(this.selectedCategory == 'ALL' || this.selectedCategory == null){
      this.loadAllProducts()
    }
    this.selectedCategory = category;
    this.loadProducts(category);
  }

  loadProducts(category: ProductCategory) {
    this.productService.getProductsByCategory(category).subscribe(
      (data) => this.products = data,
      (error) => console.error('Ошибка при получении товаров:', error)
    );
  }

  loadAllProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data) =>
        this.products = data,
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
