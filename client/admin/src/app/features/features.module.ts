import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthenticationModule } from './authentication/authentication.module';
import { UserManagementModule } from './user-management/user-management.module';
// import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { CategoryManagementModule } from './category-management/category-management.module';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { VendorListComponent } from './vendor-management/components/vendor-list/vendor-list.component';
import { CoreModule } from '../core/core.module';
import { FeaturesRoutingModule } from './features-routing.module';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddCategoryComponent } from './category-management/components/add-category/add-category.component';
import { CategoryListComponent } from './category-management/components/category-list/category-list.component';
import { PendingVendorListComponent } from './vendor-management/components/pending-vendor-list/pending-vendor-list.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DeleteCategoryComponent } from './category-management/components/delete-category/delete-category.component';
import { MatDialogModule } from '@angular/material/dialog';
import { SubCategoryListComponent } from './category-management/components/sub-category-list/sub-category-list.component';
import { AddSubcategoryComponent } from './category-management/components/add-subcategory/add-subcategory.component';
import { EditCategoryComponent } from './category-management/components/edit-category/edit-category.component';

import { EditSubcategoryComponent } from './category-management/components/edit-subcategory/edit-subcategory.component';
import { DeleteSubcategoryComponent } from './category-management/components/delete-subcategory/delete-subcategory.component';

import { NgxUiLoaderModule } from 'ngx-ui-loader';


// import { MatDialog, MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    AdminHomeComponent,
    DashboardComponent,
    VendorListComponent,
    AddCategoryComponent,
    CategoryListComponent,
    PendingVendorListComponent,
    DeleteCategoryComponent,
    SubCategoryListComponent,
    AddSubcategoryComponent,
    EditCategoryComponent,
    EditSubcategoryComponent,
    DeleteSubcategoryComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    UserManagementModule,
    AuthenticationModule,
    FeaturesRoutingModule,
    CoreModule,
    SharedModule,
    MatTooltipModule,
    MatDialogModule,
    NgxUiLoaderModule,
  ],
})
export class FeaturesModule {}
