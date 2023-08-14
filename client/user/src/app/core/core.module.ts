import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { OtpVerifyComponent } from './components/otp-verify/otp-verify.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FooterComponent } from './components/footer/footer.component';
import { SharedModule } from '../shared/shared.module';
import { VendorSidebarComponent } from './components/vendor-sidebar/vendor-sidebar.component';
import { CoreRoutingModule } from './core-routing.module';
import { BlockedComponent } from './components/blocked/blocked.component';
import { BannerComponent } from './components/banner/banner.component';
import { NavVendorComponent } from './components/nav-vendor/nav-vendor.component';
import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [
    NavComponent,
    HomeComponent,
    OtpVerifyComponent,
    FooterComponent,
    VendorSidebarComponent,
    BlockedComponent,
    BannerComponent,
    NavVendorComponent,
  ],
  imports: [
    CommonModule,MatTooltipModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
    CoreRoutingModule,
    SharedModule,
    FormsModule,
  ],
  exports: [
    NavComponent,
    OtpVerifyComponent,
    HomeComponent,
    FooterComponent,
    VendorSidebarComponent,
    BannerComponent,
    NavVendorComponent,
  ],
})
export class CoreModule {}
