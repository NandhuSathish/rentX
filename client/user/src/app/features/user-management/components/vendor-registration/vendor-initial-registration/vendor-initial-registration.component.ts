import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CredentialResponse } from 'google-one-tap';

import { environment } from 'src/environments/environment';
import { passwordMatch } from '../../../validators/passwordMatchValidators';
import { AuthService } from 'src/app/services/auth.service';
import { VendorService } from '../../../services/vendor.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-vendor-initial-registration',
  templateUrl: './vendor-initial-registration.component.html',
  styleUrls: ['./vendor-initial-registration.component.css'],
})
export class VendorInitialRegistrationComponent implements OnInit {
  private clientId = environment.clientId;
  public showPassword: boolean = false;
  public showPasswordOnPress: boolean = false;
  passwordregexp = new RegExp(
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])(?!.*\s).{8,16}$/
  );
  emailregexp = new RegExp(
    /^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/
  );

  vendorRegistrationForm!: FormGroup;
  formSubmitted = false;
  errorData: any;
  constructor(
    private renderer: Renderer2,
    private _ngZone: NgZone,
    private authService: AuthService,
    private router: Router,
    private ngxService: NgxUiLoaderService,
    private vendorService: VendorService,
    public toast: ToastService
  ) {}

  ngOnInit(): void {
    localStorage.removeItem('emailToken');
    localStorage.removeItem('timerEnded');
    this.vendorRegistrationForm = new FormGroup(
      {
        email: new FormControl('', [
          Validators.required,
          Validators.pattern(this.emailregexp),
          Validators.maxLength(50),
        ]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(this.passwordregexp),
          Validators.maxLength(16),
        ]),
        confirmPassword: new FormControl('', [Validators.required]),
      },
      [passwordMatch('password', 'confirmPassword')]
    );

    const script = this.renderer.createElement('script');
    script.src = 'https://accounts.google.com/gsi/client';
    script.onload;
    this.renderer.appendChild(document.body, script);
    // google sigin GamepadButton.
    // @ts-ignore
    window.onGoogleLibraryLoad = () => {
      // @ts-ignore
      google.accounts.id.initialize({
        client_id: this.clientId,
        callback: this.handleCredentialResponse.bind(this),
        auto_select: false,
        cancel_on_tap_outside: true,
      });
      // @ts-ignore
      google.accounts.id.renderButton(
        // @ts-ignore
        document.getElementById('buttonDiv'),
        {
          theme: 'outline',
          size: 'large',
          width: '100%',
          logo_alignment: 'center',
        }
      );
      // @ts-ignore
      google.accounts.id.prompt((notification: PromptMomentNotification) => {});
    };
  }

  // handle the response from the google client.
  handleCredentialResponse(response: CredentialResponse) {
    this.vendorService.vendorLoginWithGoogle(response.credential).subscribe({
      next: (res) => {
        if (res.role === 0) {
          this.authService.storeToken(
            res.accessToken.value,
            res.refreshToken.value,
            res.role,
            res.status
          );
          this._ngZone.run(() => {
            this.router.navigate(['/home']);
          });
        } else if (res.role === 1) {
          switch (res.status) {
            case 3:
              this.authService.stagedVendorToken(
                res.emailToken,
                res.role,
                res.status
              );
              this._ngZone.run(() => {
                this.router.navigate(['/vendorRegisterFinal']);
              });

              break;
            case 4:
              this.authService.stagedVendorToken(
                res.emailToken,
                res.role,
                res.status
              );
              this._ngZone.run(() => {
                this.router.navigate(['/vendorRegisterFinal']);
              });
              break;

            case 5:
              this.authService.storeUserDetails(res.role, res.status);
              this._ngZone.run(() => {
                this.router.navigate(['/waitingPage']);
              });

              break;
            case 1:
              this._ngZone.run(() => {
                this.router.navigate(['/vendorRegistration']);
              });
              break;
            case 0:
              this.authService.storeToken(
                res.accessToken.value,
                res.refreshToken.value,
                res.role,
                res.status
              );
              this._ngZone.run(() => {
                this.router.navigateByUrl('vendor/dashboard');
              });
              break;
            default:
              break;
          }
        }
      },
      error: (error) => {
        if (error.error.errorCode == '5001') {
          this._ngZone.run(() => {
            this.router.navigateByUrl('/blockedPage');
          });
        } else {
          this.errorData = error.error.errorMessage;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
        }
      },
    });
  }
  onSubmit() {
    this.formSubmitted = true;
    if (this.vendorRegistrationForm.valid) {
      this.ngxService.start();
      this.vendorService
        .vendorRegistration(
          this.vendorRegistrationForm.value.email,
          this.vendorRegistrationForm.value.password
        )
        .subscribe({
          next: (data: any) => {
            this.authService.storeEmailToken(data.emailToken);
            this.ngxService.stop();
            this._ngZone.run(() => {
              this.router.navigateByUrl('/vendorOtpVerify');
            });
          },
          error: (data: any) => {
            this.ngxService.stop();
            this.errorData = data.error.errorMessage;
            this.errorData =
              this.errorData.charAt(0).toUpperCase() +
              this.errorData.slice(1).toLowerCase();
            this.toast.showErrorToast(this.errorData, 'close');
          },
        });
    } else {
      return;
    }
  }
}
