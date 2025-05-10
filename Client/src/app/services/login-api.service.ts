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

  constructor(private httpClient: HttpClient) {

  }

  //api call for user login
  login(email: string, password: string): Observable<any> {
    const payload = { email, password };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.httpClient.post(`${this.apiUrl}/login`, payload, {
      headers,
      withCredentials: false
    });
  }

}
