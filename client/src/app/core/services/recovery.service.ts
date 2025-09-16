import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UpdatePasswordRequest} from '../interface/update-password';

@Injectable({
  providedIn: 'root',
})
export class RecoveryService {
  http = inject(HttpClient);
  baseApiUrl = 'http://localhost:8080/recovery/';

  updatePassword(request: UpdatePasswordRequest): Observable<string> {
    return this.http.post<string>(`${this.baseApiUrl}update-password`, request);
  }

}
