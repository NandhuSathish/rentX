import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PosHomeComponent } from './components/pos-home/pos-home.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductsComponent } from './components/products/products.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';

const routes: Routes = [
  {
    path: '',
    component: PosHomeComponent,
  },
  {
    path: 'home',
    component: PosHomeComponent,
  },
  {
    path: 'detail/:product',
    component: ProductDetailComponent,
  },
  {
    path: ':products',
    component: ProductListComponent,
    // canActivate: [VendorGuard],
    // children: [
    //   {
    //     path: ':productCategory',
    //     component: ProductsComponent,
    //   },
    //   {
    //     path: 'search/:searchQuery',
    //     component: ProductsComponent,
    //   },
    //   {
    //     path: '',
    //     redirectTo: ':productCategory',
    //     pathMatch: 'full',
    //   },
    // ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PosRoutingModule {}
