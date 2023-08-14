import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CredentialResponse } from 'google-one-tap';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';
import { UserService } from '../../services/user.service';
import { passwordMatch } from '../../validators/passwordMatchValidators';

import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-user-registraion',
  templateUrl: './user-registraion.component.html',
  styleUrls: ['./user-registraion.component.css'],
})
export class UserRegistraionComponent implements OnInit {
  formSubmitted = false;
  emailregexp = new RegExp(
    /^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/
  );

  passwordregexp = new RegExp(
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])(?!.*\s).{8,16}$/
  );
  private clientId = environment.clientId;
  userRegistrationForm!: FormGroup;
  public showPassword: boolean = false;
  public showPasswordOnPress: boolean = false;
  errorData: any;

  constructor(
    private router: Router,
    private authService: AuthService,
    private _ngZone: NgZone,
    private userService: UserService,
    private renderer: Renderer2,
    public toast: ToastService,
    private ngxService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    localStorage.removeItem('emailToken');
    localStorage.removeItem('timerEnded');
    const script = this.renderer.createElement('script');
    script.src = 'https://accounts.google.com/gsi/client';
    script.onload;
    this.renderer.appendChild(document.body, script);

    this.userRegistrationForm = new FormGroup(
      {
        email: new FormControl('', [
          Validators.required,
          Validators.nullValidator,
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
    this.authService.loginWithGoogle(response.credential).subscribe({
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
    if (this.userRegistrationForm.valid) {
      this.ngxService.start();
      this.userService
        .register(
          this.userRegistrationForm.value.email,
          this.userRegistrationForm.value.password
        )
        .subscribe({
          next: (data: any) => {
            this.authService.storeEmailToken(data.emailToken);
            this.ngxService.stop();
            this._ngZone.run(() => {
              this.router.navigateByUrl('/otpVerify');
            });
          },
          error: (data) => {
            this.ngxService.stop();
            this.toast.showErrorToast(data.error.errorMessage, 'close');
          },
        });
    } else {
      return;
    }
  }
}
