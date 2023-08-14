import { NgModule } from '@angular/core';
import { CommonModule, JsonPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from './components/input/input.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SucessToastComponent } from './components/toast/sucess-toast/sucess-toast.component';
import { ErrorToastComponent } from './components/toast/error-toast/error-toast.component';
import { AlertComponent } from './components/dialogs/alert/alert.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule, MatHint } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ImageUploadComponent } from './components/image-upload/image-upload.component';
import { DeleteToastComponent } from './components/toast/delete-toast/delete-toast.component';

@NgModule({
  declarations: [
    InputComponent,
    SucessToastComponent,
    ErrorToastComponent,
    AlertComponent,
    ImageUploadComponent,
    DeleteToastComponent,
  ],

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    MatNativeDateModule,
  ],
  exports: [InputComponent],
})
export class SharedModule {}
