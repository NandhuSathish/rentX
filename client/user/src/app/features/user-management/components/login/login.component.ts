import { HttpErrorResponse } from '@angular/common/http';
import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CredentialResponse } from 'google-one-tap';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  private clientId = environment.clientId;
  public showPassword: boolean = false;
  public showPasswordOnPress: boolean = false;
  loginForm!: FormGroup;
  responseData: any;
  errorData: any;
  formSubmitted = false;
  emailregexp = new RegExp(
    /^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/
  );
  constructor(
    public route: Router,
    private authService: AuthService,
    private _ngZone: NgZone,
    private renderer: Renderer2,
    public toast: ToastService
  ) {}

  ngOnInit(): void {
    localStorage.clear();
    localStorage.removeItem('emailToken');
    this.loginForm = new FormGroup({
      email: new FormControl('', [
        Validators.pattern(this.emailregexp),
        Validators.required,
      ]),
      password: new FormControl('', [Validators.required]),
    });

    const script = this.renderer.createElement('script');
    script.src = 'https://accounts.google.com/gsi/client';
    script.onload;
    this.renderer.appendChild(document.body, script);
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
            this.route.navigate(['/home']);
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
                this.route.navigate(['/vendorRegisterFinal']);
              });

              break;
            case 4:
              this.authService.stagedVendorToken(
                res.emailToken,
                res.role,
                res.status
              );
              this._ngZone.run(() => {
                this.route.navigate(['/vendorRegisterFinal']);
              });
              break;

            case 5:
              this.authService.storeUserDetails(res.role, res.status);
              this._ngZone.run(() => {
                this.route.navigate(['/waitingPage']);
              });
              break;
            case 1:
              this._ngZone.run(() => {
                this.route.navigate(['/vendorRegistration']);
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
                this.route.navigateByUrl('vendor/dashboard');
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
            this.route.navigateByUrl('/blockedPage');
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
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (res) => {
          if (res.role === 0) {
            this.authService.storeToken(
              res.accessToken.value,
              res.refreshToken.value,
              res.role,
              res.status
            );
            this._ngZone.run(() => {
              this.route.navigate(['/home']);
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
                  this.route.navigate(['/vendorRegisterFinal']);
                });

                break;
              case 4:
                this.authService.stagedVendorToken(
                  res.emailToken,
                  res.role,
                  res.status
                );
                this._ngZone.run(() => {
                  this.route.navigate(['/vendorRegisterFinal']);
                });
                break;

              case 5:
                this.authService.storeUserDetails(res.role, res.status);
                this._ngZone.run(() => {
                  this.route.navigate(['/waitingPage']);
                });

                break;
              case 1:
                this._ngZone.run(() => {
                  this.route.navigate(['/vendorRegistration']);
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
                  this.route.navigateByUrl('vendor/dashboard');
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
              this.route.navigateByUrl('/blockedPage');
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
    } else {
      return;
    }
  }
}
