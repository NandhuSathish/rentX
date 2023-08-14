import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private apiURL = environment.apiUrl;
  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  vendorLoginWithGoogle(credentials: string): Observable<any> {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.post(
      `${this.apiURL}/vendor/googleAuth`,
      { token: credentials },
      { headers: header }
    );
  }
  // vendor registration.
  vendorRegistration(email: string, password: string) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.post(
      `${this.apiURL}/vendor/registration`,
      {
        email: email,
        password: password,
      },
      { headers: header }
    );
  }
  // vendor registration stage one.
  vendorRegistrationStageOne(name: string, mobNo: string) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.put(
      `${this.apiURL}/vendor/registration`,
      {
        username: name,
        phone: mobNo,
        emailToken: localStorage.getItem('emailToken'),
      },
      { headers: header }
    );
  }
  // vendor registration stage three bank details.
  addVendorBankDetails(bankForm: any) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.post(
      `${this.apiURL}/vendor/bankDetails`,
      {
        accountNumber: bankForm.value.accountNo,
        holderName: bankForm.value.accountHolderName,
        ifsc: bankForm.value.ifscCode,
        gst: bankForm.value.gstNo,
        pan: bankForm.value.panNo,
        emailToken: localStorage.getItem('emailToken'),
      },
      {
        headers: header,
      }
    );
  }

  // verify otp.
  verifyVendorOtp(otp: string) {
    const header = new HttpHeaders().set('Content-type', 'application/json');
    const emailToken = localStorage.getItem('emailToken');

    return this.httpClient.post(
      `${this.apiURL}/vendor/otp/verify`,
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
