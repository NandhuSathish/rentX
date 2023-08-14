import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from './components/input/input.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SucessToastComponent } from './components/toast/sucess-toast/sucess-toast.component';
import { ErrorToastComponent } from './components/toast/error-toast/error-toast.component';
import { MatDialogModule } from '@angular/material/dialog';
import { AlertComponent } from './components/dialogs/alert/alert.component';
import { WarningToastComponent } from './components/toast/warning-toast/warning-toast.component';
import { DeleteToastComponent } from './components/toast/delete-toast/delete-toast.component';

@NgModule({
  declarations: [InputComponent, SucessToastComponent, ErrorToastComponent,AlertComponent, WarningToastComponent, DeleteToastComponent],

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatDialogModule,
  ],
  exports: [InputComponent],
})
export class SharedModule {}
