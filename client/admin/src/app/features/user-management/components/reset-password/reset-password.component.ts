import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { passwordMatch } from '../../validators/passwordMatchValidators';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError, of } from 'rxjs';
import { ToastService } from 'src/app/shared/services/toast.service';
@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
})
export class ResetPasswordComponent implements OnInit {
  formSubmitted = false;
  public showPassword: boolean = false;
  public showPasswordOnPress: boolean = false;

  ResetPasswordForm: any;
  emailregexp = new RegExp(
    /^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/
  );
  passwordregexp = new RegExp(
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,16}$/
  );
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  responseData: any;
  errorData: any;

  constructor(
    public toast: ToastService,
    public route: Router,
    private authService: AuthService,
    private _snackBar: MatSnackBar
  ) {
    /* TODO document why this constructor is empty */
  }
  ngOnInit(): void {
    localStorage.removeItem('form');
    this.ResetPasswordForm = new FormGroup(
      {
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
  }
  openSnackBar(message: string) {
    this._snackBar.open(message, 'close', {
      duration: 5 * 1000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  onSubmit() {
    this.formSubmitted = true;
    if (this.ResetPasswordForm.valid) {
      this.authService
        .ResetPassword(this.ResetPasswordForm.value.password)
        .pipe(
          catchError((error: HttpErrorResponse) => {
            this.errorData = error;

            if (error.error.errorMessage) {
              this.toast.showErrorToast(error.error.errorMessage, 'close');
            }

            return of(null);
          })
        )
        .subscribe((result) => {
          if (result) {
            this.openSnackBar(result.message);
            this.toast.showSucessToast(result.message, 'close');

            this.responseData = result;
            this.route.navigateByUrl('/');
            localStorage.clear();
          }
        });
    } else {
      this.ResetPasswordForm.markAllAsTouched();
    }
  }
}
