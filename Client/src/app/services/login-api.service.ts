import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class LoginApiService {
    apiUrl: string = 'http://localhost:8080/api/users';
    reCaptchaVerificationApiUrl: string = '';
    headers = new HttpHeaders();

    constructor(private httpClient: HttpClient){

    }

    login(email: string, password: string): Observable<any> {
      const payload = { email, password };
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json' // <-- Add this line
      });
    
      return this.httpClient.post(`${this.apiUrl}/login`, payload, {
        headers,
        withCredentials: false
      });
    }
    
    
      register(userData: any): Observable<any> {
        return this.httpClient.post(`${this.apiUrl}/register`, userData);
      }
}
