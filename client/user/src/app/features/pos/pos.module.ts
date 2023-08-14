import { NgModule } from '@angular/core';
import { CommonModule, JsonPipe } from '@angular/common';

import { PosRoutingModule } from './pos-routing.module';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { PosHomeComponent } from './components/pos-home/pos-home.component';
import { ProductsComponent } from './components/products/products.component';
import { CoreModule } from 'src/app/core/core.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule, MatHint } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [
    ProductListComponent,
    ProductDetailComponent,
    PosHomeComponent,
    ProductsComponent,
  ],
  imports: [
    CommonModule,
    PosRoutingModule,
    CoreModule,
    SharedModule,
    MatDatepickerModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    MatNativeDateModule,
    MatInputModule,
  ],
})
export class PosModule {}
