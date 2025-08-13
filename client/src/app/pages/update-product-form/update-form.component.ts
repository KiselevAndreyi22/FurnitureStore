import { Component } from '@angular/core'
import { ProductService} from '../../core/services/product.service';
import {FormsModule} from '@angular/forms';
import { IProductUpdate } from '../../core/interface/product.interface';
import {ActivatedRoute} from '@angular/router';

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
  product: IProductUpdate = { id: 0, name: '', description: '', price: 0 };
  productId!: number;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.productId = +idParam;
      }
    });
  }

  onSubmit() {
    this.productService.updateProduct(this.productId, this.product).subscribe(
      response => {
        alert('Продукт обновлен!');
        console.log(response);
      },
      error => {
        alert('Ошибка при создании продукта');
        console.error(error);
      }
    );
  }
}
