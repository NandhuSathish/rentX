import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiURL = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  // user registration.
  register(email: string, password: string) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.post(
      `${this.apiURL}/user/add`,
      {
        email: email,
        password: password,
      },
      { headers: header }
    );
  }

  // verify otp.
  verifyOtp(otp: string) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    const emailToken = localStorage.getItem('emailToken');
    return this.httpClient.post(
      `${this.apiURL}/otp/verify`,
      {
        otp: otp,
        emailToken,
      },
      {
        headers: header,
      }
    );
  }
}
