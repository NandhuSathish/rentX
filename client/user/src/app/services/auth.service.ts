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
    private router: Router
  ) {}

  login(data: any): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/login`, data);
  }

  loginWithGoogle(credentials: string): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/user/googleAuth`, {
      token: credentials,
    });
  }

  logOut() {
    this._ngZone.run(() => {
      localStorage.clear();
      this.router.navigateByUrl('/home');
    });
  }
  ForgotPassword(data: any): Observable<any> {
    return this.httpClient.post(`${this.apiURL}/forgetPassword`, data);
  }

  ResetPassword(password: any): Observable<any> {
    const emailToken = localStorage.getItem('passwordToken');
    return this.httpClient.put(`${this.apiURL}/changePassword`, {
      password: password,
      emailToken: emailToken,
    });
  }

  OtpVerify(otp: any): Observable<any> {
    const emailToken = localStorage.getItem('emailToken');
    return this.httpClient.post(`${this.apiURL}/otp/verify/forgetpassword`, {
      otp: otp,
      emailToken: emailToken,
    });
  }
  forgotResendOtp(): Observable<any> {
    const emailToken = localStorage.getItem('emailToken');
    console.log(emailToken);
    return this.httpClient.post(`${this.apiURL}/otp/resend/0 `, {
      emailToken: emailToken,
    });
  }
  resendOtp(): Observable<any> {
    const emailToken = localStorage.getItem('emailToken');
    console.log(emailToken);
    return this.httpClient.post(`${this.apiURL}/otp/resend/1 `, {
      emailToken: emailToken,
    });
  }
  refreshToken() {
    let token = localStorage.getItem('refreshToken');
    const tokenObject = {
      refreshToken: token,
    };
    console.log('refreshtokken generated');
    return this.httpClient.put(`${this.apiURL}/login`, tokenObject);
  }

  //store tokens in local storage.
  storeToken(
    Token: String,
    Refreshtoken: String,
    Role: String,
    Status: String
  ) {
    localStorage.setItem('token', Token.toString());
    localStorage.setItem('refreshToken', Refreshtoken.toString());
    localStorage.setItem('role', Role.toString());
    localStorage.setItem('status', Status.toString());
  }

  newToken(Token: String, Refreshtoken: String) {
    localStorage.setItem('token', Token.toString());
    localStorage.setItem('refreshToken', Refreshtoken.toString());
  }

  stagedVendorToken(emailToken: String, Role: String, Status: String) {
    localStorage.setItem('emailToken', emailToken.toString());
    localStorage.setItem('role', Role.toString());
    localStorage.setItem('status', Status.toString());
  }

  storeUserDetails(Role: String, Status: String) {
    localStorage.setItem('role', Role.toString());
    localStorage.setItem('status', Status.toString());
  }

  storeEmailToken(token: String) {
    localStorage.setItem('emailToken', token.toString());
  }

  getEmailToken() {
    localStorage.getItem('emailToken');
  }

  // is the user aunthenticated ?
  isAuthenticated(): boolean {
    if (localStorage.getItem('token')) {
      return true;
    } else {
      return false;
    }
  }

  notAuthenticated(): boolean {
    if (localStorage.getItem('token')) {
      return false;
    } else {
      return true;
    }
  }

  IsLoggedIn() {
    return localStorage.getItem('token') != null;
  }
  GetToken() {
    return localStorage.getItem('token') || '';
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
  RoleGuard() {
    const role = localStorage.getItem('role');
    const status = localStorage.getItem('status');

    if ((role === '1' && status === '3') || status === '4') {
      return true;
    } else {
      return false;
    }
  }
}
