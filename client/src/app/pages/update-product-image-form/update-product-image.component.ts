import { Component } from '@angular/core'
import { ProductService} from '../../core/services/product.service';
import {FormsModule} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'update-product-image-form',
  templateUrl: 'update-product-image.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./update-product-image.component.scss']
})
export class UploadImageFormComponent{
  product = {
    name: '',
    description: '',
    price: null
  };

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  selectedFile: File | null = null;
  productId: number | null = null;

  ngOnInit(): void {
    // Получаем id из параметров маршрута
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.productId = id ? +id : null; // Преобразуем строку в число
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  onUploadImage(productId: number): void {
    if (this.selectedFile) {
      this.productService.uploadImage(productId, this.selectedFile)
        .subscribe({
          next: (response) => {
            alert('Изображение успешно загружено!');
          },
          error: (err) => {
            alert('Ошибка при загрузке изображения');
          }
        });
    }
  }

  onSubmit(): void {
    if (this.productId && this.selectedFile) {
      this.onUploadImage(this.productId);
    } else {
      alert('Не выбран продукт или файл!');
    }
  }

}
