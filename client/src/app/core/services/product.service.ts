import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IProduct } from '../interface/product.interface';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseApiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  createProduct(product: any): Observable<any> {
    return this.http.post(`${this.baseApiUrl}/create`, product);
  }

  deleteProduct(id: number) {
    return this.http.delete(`${this.baseApiUrl}/${id}`);
  }

  getUserProducts(): Observable<IProduct[]> {
    return this.http.get<IProduct[]>('http://localhost:8080/api/products/me');
  }

  getAllProducts(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/products/all');
  }
}
