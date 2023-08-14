import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { VendorHomeComponent } from './components/vendor-home/vendor-home.component';
import { VendorDashboardComponent } from './components/vendor-dashboard/vendor-dashboard.component';
import { ProductListComponent } from './components/product/product-list/product-list.component';
import { StoreListComponent } from './components/store/store-list/store-list.component';
import { VendorOrderComponent } from './components/vendor-order/vendor-order.component';
import { VendorReportComponent } from './components/vendor-report/vendor-report.component';
import { AddStoreComponent } from './components/store/add-store/add-store.component';
import { VendorGuard } from 'src/app/guards/vendor.guard';
import { AddProductComponent } from './components/product/add-product/add-product.component';
import { VendoProductDetailViewComponent } from './components/product/vendo-product-detail-view/vendo-product-detail-view.component';

const routes: Routes = [
  {
    path: '',
    component: VendorHomeComponent,
    children: [
      {
        path: 'dashboard',
        component: VendorDashboardComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'products',
        component: ProductListComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'addProduct',
        component: AddProductComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'stores',
        component: StoreListComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'productDeailViewVendor/:id',
        component: VendoProductDetailViewComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'addStore',
        component: AddStoreComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'orders',
        component: VendorOrderComponent,  canActivate: [VendorGuard],
      },
      {
        path: 'reports',
        component: VendorReportComponent,  canActivate: [VendorGuard],
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VendorRoutingModule {}
