import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MailService {
  http = inject(HttpClient);
  baseApiUrl = 'http://localhost:8080/mail/';

  sendVerificationEmail(to: string): Observable<string> {
    const params = new HttpParams().set('to', to);
    return this.http.post(`${this.baseApiUrl}send-verify`, null, { params, responseType: 'text' });
  }

}
