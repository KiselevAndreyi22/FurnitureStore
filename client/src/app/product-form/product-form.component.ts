import { Component } from '@angular/core'
import { ProductService} from '../core/services/product.service';
import {FormsModule} from '@angular/forms';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent {
  product = {
    name: '',
    description: '',
    price: null,
    tags: [] as string[]
  };

  newTag = '';

  constructor(private productService: ProductService) {}

  addTag() {
    const tag = this.newTag.trim();
    if (tag && !this.product.tags.includes(tag)) {
      this.product.tags.push(tag);
      this.newTag = '';
    }
  }

  removeTag(index: number) {
    this.product.tags.splice(index, 1);
  }

  onSubmit() {
    const payload = {
      name: this.product.name,
      description: this.product.description,
      price: this.product.price,
      tags: this.product.tags.map(tag => ({ name: tag }))
    };

    this.productService.createProduct(payload).subscribe(
      response => {
        alert('Продукт создан!');
        console.log(response);
      },
      error => {
        alert('Ошибка при создании продукта');
        console.error(error);
      }
    );
  }
}


