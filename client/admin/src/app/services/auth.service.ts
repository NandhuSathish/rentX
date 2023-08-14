import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiURL = environment.apiUrl;
  constructor(
    private httpClient: HttpClient,
    private _ngZone: NgZone,
    private router: Router,

  ) {}

  storeToken(token: String, Refreshtoken: String) {
    localStorage.setItem('token', token.toString());
    localStorage.setItem('refreshToken', Refreshtoken.toString());
  }
  newToken(Token: String, Refreshtoken: String) {
    localStorage.setItem('token', Token.toString());
    localStorage.setItem('refreshToken', Refreshtoken.toString());
  }

  storeEmailToken(token: String) {
    localStorage.setItem('emailToken', token.toString());
  }
  getEmailToken() {
    localStorage.getItem('emailToken');
  }
  // is the user aunthenticated ?
  isAuthenticated(): boolean {
    if (localStorage.getItem('token') ) {
      return true;
    } else {
      return false;
    }
  }
  forgotResendOtp(): Observable<any> {
    const emailToken = localStorage.getItem('emailToken');
    console.log(emailToken)
    return this.httpClient.post(`${this.apiURL}/otp/resend/0`, {
      emailToken: emailToken,
    });
  }
  notAuthenticated(): boolean {
    if (localStorage.getItem('token')) {
      return false;
    } else {
      return true;
    }
  }
  refreshToken() {
    let token = localStorage.getItem('refreshToken');
    return this.httpClient.put(`${this.apiURL}/admin/login`, token);
    
  }
  logOut() {
    this._ngZone.run(() => {
      localStorage.clear();
      this.router.navigateByUrl('/');
    });
  }

  ForgotPassword(data: any): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/admin/forgetPassword`, data);
  }
  OtpVerify(otp: any): Observable<any> {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    const emailToken = localStorage.getItem('emailToken');
    return this.httpClient.post(
      `${this.apiURL}/admin/otp/verify/forgetpassword`,
      {
        otp: otp,
        emailToken: emailToken,
      },
      {
        headers: header,
      }
    );
  }
  ResetPasswordGuard() {
    if (localStorage.getItem('passwordToken')) {
      return true;
    } else {
      return false;
    }
  }
  otpGuard() {
    if (localStorage.getItem('emailToken')) {
      return true;
    } else {
      return false;
    }
  }
  ResetPassword(password: any): Observable<any> {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    const emailToken = localStorage.getItem('passwordToken');
    return this.httpClient.put(
      `${this.apiURL}/admin/changePassword`,
      {
        password: password,
        emailToken: emailToken,
      },
      {
        headers: header,
      }
    );
  }

  login(data: any): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/admin/login`, data);
  }
  IsLoggedIn() {
    return localStorage.getItem('token') != null;
  }
  GetToken() {
    return localStorage.getItem('token') || '';
  }
}
