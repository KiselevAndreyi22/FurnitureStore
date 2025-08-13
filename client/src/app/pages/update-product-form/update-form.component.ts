import { Component } from '@angular/core'
import { ProductService} from '../../core/services/product.service';
import {FormsModule} from '@angular/forms';
import { IProductUpdate } from '../../core/interface/product.interface';

@Component({
  selector: 'app-update-form',
  templateUrl: 'update-form.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./update-form.component.scss'],
  standalone: true
})
export class UpdateFormComponent{
  product : IProductUpdate = {
    name: '',
    description: '',
    price: 0
  };

  constructor(private productService: ProductService) {}

  onSubmit(){
    const id = 20;
    this.productService.updateProduct(id, this.product).subscribe(response => {alert('Продукт обновлен!');
      console.log(response);
    }, error => {
      alert('Ошибка при обновлении продукта');
      console.error(error);
    });
  }
}
