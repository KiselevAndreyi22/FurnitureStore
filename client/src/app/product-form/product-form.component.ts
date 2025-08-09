import { Component } from '@angular/core'
import { ProductService} from '../core/services/product.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent{
  product = {
    name: '',
    description: '',
    price: null
  };

  constructor(private productService: ProductService) {}

  onSubmit(){
    this.productService.createProduct(this.product).subscribe(response => {alert('Продукт создан!');
    console.log(response);
    }, error => {
      alert('Ошибка при создании продукта');
      console.error(error);
      });
  }
}
