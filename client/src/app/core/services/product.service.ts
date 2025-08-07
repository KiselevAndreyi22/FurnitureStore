import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/products/create';

  constructor(private http: HttpClient) {}

  createProduct(product: any): Observable<any> {
    return this.http.post(this.apiUrl, product);
  }

}
