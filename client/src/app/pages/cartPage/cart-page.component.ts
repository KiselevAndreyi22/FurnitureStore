import {CommonModule} from '@angular/common';
import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {ProductService} from '../../core/services/product.service';
import {FormsModule} from '@angular/forms';
import {IProductCart} from '../../core/interface/product.interface';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  imports: [RouterLink, CommonModule, FormsModule],
  styleUrl: './cart-page.component.scss',
})
export class CartPageComponent {
  products: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.loadAllProducts();
  }

  loadAllProducts() {
    this.productService.getAllProductsFromCart().subscribe({
      next: (data) =>
        this.products = data,
      error: (err) => console.error(err)
    });
  }

  deleteProductFromCart(id: number) {
    this.productService.deleteFromCart(id).subscribe({
      next: () => {
        this.products = this.products.filter(product => product.product.id !== id);
      },
      error: (err) => {
        console.error('Ошибка при удалении:', err);
      }
    });
  }

  putInCart(id: number){
    this.productService.putInCart(id).subscribe({
      next: () => {
        console.log('Товар добавлен в корзину!');
      },
      error: (err) => {
        console.error('Ошибка при добавлении:', err);
      }
    })
  }
}
