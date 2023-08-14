import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';

import { VendorRoutingModule } from './vendor-routing.module';
import { VendorDashboardComponent } from './components/vendor-dashboard/vendor-dashboard.component';
import { StoreListComponent } from './components/store/store-list/store-list.component';
import { ProductListComponent } from './components/product/product-list/product-list.component';
import { VendorHomeComponent } from './components/vendor-home/vendor-home.component';
import { CoreModule } from 'src/app/core/core.module';
import { VendorOrderComponent } from './components/vendor-order/vendor-order.component';
import { VendorReportComponent } from './components/vendor-report/vendor-report.component';
import { AddStoreComponent } from './components/store/add-store/add-store.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AddProductComponent } from './components/product/add-product/add-product.component';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { MatTooltipModule } from '@angular/material/tooltip';
import { VendoProductDetailViewComponent } from './components/product/vendo-product-detail-view/vendo-product-detail-view.component';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
@NgModule({
  declarations: [
    VendorDashboardComponent,
    StoreListComponent,
    ProductListComponent,
    VendorHomeComponent,
    VendorOrderComponent,
    VendorReportComponent,
    AddStoreComponent,
    AddProductComponent,
    VendoProductDetailViewComponent,
  ],
  imports: [
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatSelectModule,
    CoreModule,
    CommonModule,
    VendorRoutingModule,
    ReactiveFormsModule,
    MatTooltipModule,
    NgxUiLoaderModule,
    FormsModule,
  ],
})
export class VendorModule {}
