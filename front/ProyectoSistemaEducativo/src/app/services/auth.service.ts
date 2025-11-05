import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth/login';

  constructor( private http: HttpClient) { }

  login(credencials: {
    email: string,
    password: string
  }): Observable<any> {
    return this.http.post(this.apiUrl, credencials);
  }
}
