import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser} from '../interface/user.interface';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  http = inject(HttpClient);
  baseApiUrl = 'http://localhost:8080/api/';

  getMe(): Observable<IUser> {
    return this.http.get<IUser>(`${this.baseApiUrl}user/me`);
  }

  updateAvatar(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${this.baseApiUrl}user/avatar`, formData /*, { headers }*/);
  }

  constructor() {}
}
