import { HttpErrorResponse } from '@angular/common/http';
import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css'],
})
export class AdminLoginComponent implements OnInit {
  private clientId = environment.clientId;
  public showPassword: boolean = false;
  public showPasswordOnPress: boolean = false;
  formSubmitted = false;
  emailregexp = new RegExp(
    /^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/
  );
  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.pattern(this.emailregexp),
    ]),
    password: new FormControl('', [Validators.required]),
  });
  responseData: any;
  errorData: any;

  constructor(
    public toast: ToastService,
    public route: Router,
    private authService: AuthService
  ) {
    /* TODO document why this constructor is empty */
  }

  ngOnInit(): void {
    localStorage.clear();
  }

  onSubmit() {
    this.formSubmitted = true;
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (result) => {
          this.toast.showSucessToast('Logged In', 'close');
          this.route.navigateByUrl('/dashboard');
          this.authService.storeToken(
            result.accessToken.value,
            result.refreshToken.value
          );
        },
        error: (error) => {
          this.errorData = error.error.errorMessage;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
        },
      });
    }
  }
}
