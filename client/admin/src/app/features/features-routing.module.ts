import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { VendorListComponent } from './vendor-management/components/vendor-list/vendor-list.component';
import { AdminDashboardComponent } from '../core/components/admin-dashboard/admin-dashboard.component';
import { CategoryListComponent } from './category-management/components/category-list/category-list.component';
import { PendingVendorListComponent } from './vendor-management/components/pending-vendor-list/pending-vendor-list.component';
import { SubCategoryListComponent } from './category-management/components/sub-category-list/sub-category-list.component';
import { AuthGuard } from '../auth.guard';
import { UserListComponent } from './user-management/components/user-list/user-list.component';

const routes: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,canActivate: [AuthGuard],
      },
      {
        path: 'vendors',
        component: VendorListComponent,canActivate: [AuthGuard],
      },
      {
        path: 'pendingVendors',
        component: PendingVendorListComponent,canActivate: [AuthGuard],
      },
      {
        path: 'categories',
        component: CategoryListComponent,canActivate: [AuthGuard],
      },
      {
        path: 'subcategories/:categoryid',
        component: SubCategoryListComponent
      },
      {
        path: 'users',
        component: UserListComponent,
      },
      // {
      //   path: 'orders',
      //   component: VendorOrderComponent,
      // },
      // {
      //   path: 'reports',
      //   component: VendorReportComponent,
      // },
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
export class FeaturesRoutingModule {}
