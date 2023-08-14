import { NgModule } from '@angular/core';
import {  CommonModule, JsonPipe } from '@angular/common';
import { CartComponent } from './components/cart/cart.component';
import { CoreModule } from 'src/app/core/core.module';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    CartComponent
  ],
  imports: [
    CommonModule, CoreModule, SharedModule,
    MatDatepickerModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    MatNativeDateModule,
    MatInputModule,
  ]
})
export class CartModule { }
