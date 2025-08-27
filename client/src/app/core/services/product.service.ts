import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {IProduct, IProductUpdate} from '../interface/product.interface';

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

  updateProduct(id: number, productForm: IProductUpdate): Observable<any>{
    return this.http.put<IProductUpdate>(`${this.baseApiUrl}/${id}`, productForm);
  }

  uploadImage(productId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    const url = `${this.baseApiUrl}/${productId}/upload-image`;
    return this.http.post(url, formData);
  }

  getProductsByCategory(category: string): Observable<IProduct[]> {
    return this.http.get<IProduct[]>(`${this.baseApiUrl}/all/${category}`);
  }

  putInCart(id: number): Observable<any>{
    return this.http.post(`${this.baseApiUrl}/${id}/put-in-cart`, id);
  }

}
