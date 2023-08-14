import { Injectable } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { SucessToastComponent } from '../components/toast/sucess-toast/sucess-toast.component';
import { ErrorToastComponent } from '../components/toast/error-toast/error-toast.component';
import { DeleteToastComponent } from '../components/toast/delete-toast/delete-toast.component';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  constructor(private _snackBar: MatSnackBar) {}
  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  showSucessToast(message: string, action: string) {
    this._snackBar.openFromComponent(SucessToastComponent, {
      data: {
        message: message,
        action: action,
        snackBar: this._snackBar,
      },
      duration: 3 * 1000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
  showErrorToast(message: string, action: string) {
    this._snackBar.openFromComponent(ErrorToastComponent, {
      data: {
        message: message,
        action: action,
        snackBar: this._snackBar,
      },
      duration: 3 * 1000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
  showDeleteToast(message: string, action: string) {
    this._snackBar.openFromComponent(DeleteToastComponent, {
      data: {
        message: message,
        action: action,
        snackBar: this._snackBar,
      },
      duration: 3 * 1000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
