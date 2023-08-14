import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementModule } from './user-management/user-management.module';
import { VendorModule } from './vendor/vendor.module';
import { PosModule } from './pos/pos.module';
import { CartModule } from './cart/cart.module';
import { CoreModule } from '../core/core.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, UserManagementModule,CoreModule, VendorModule, PosModule,CartModule],
})
export class FeaturesModule {}
