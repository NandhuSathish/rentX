import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRegistraionComponent } from './features/user-management/components/user-registraion/user-registraion.component';
import { LoginComponent } from './features/user-management/components/login/login.component';
import { HomeComponent } from './core/components/home/home.component';
import { OtpVerifyComponent } from './core/components/otp-verify/otp-verify.component';
import { AuthGuard } from './auth.guard';

import { ResetPasswordComponent } from './features/user-management/components/reset-password/reset-password.component';
import { ResetpasswordGuard } from './guards/resetpassword.guard';
import { UnAuthGuard } from './guards/un-auth.guard';

import { VendorInitialRegistrationComponent } from './features/user-management/components/vendor-registration/vendor-initial-registration/vendor-initial-registration.component';
import { VendorFinalRegistrationComponent } from './features/user-management/components/vendor-registration/vendor-final-registration/vendor-final-registration.component';
import { OtpGuard } from './guards/otp.guard';
import { VendorOtpVerifyComponent } from './features/user-management/components/vendor-otp-verify/vendor-otp-verify.component';
import { RoleGuard } from './guards/role.guard';
import { ForgetPasswordComponent } from './features/user-management/components/forget-password/forget-password.component';
import { VendorGuard } from './guards/vendor.guard';
import { VendorDashboardComponent } from './features/vendor/components/vendor-dashboard/vendor-dashboard.component';
import { WaitingPageComponent } from './features/user-management/components/waiting-page/waiting-page.component';
import { BlockedComponent } from './core/components/blocked/blocked.component';
import { ImageUploadComponent } from './shared/components/image-upload/image-upload.component';
import { CartComponent } from './features/cart/components/cart/cart.component';

const routes: Routes = [
  {
    path: 'register',
    component: UserRegistraionComponent,
    canActivate: [UnAuthGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [UnAuthGuard],
  },
  {
    path: 'vendorRegisterInitial',
    component: VendorInitialRegistrationComponent,
  },
  {
    path: 'testImage',
    component: ImageUploadComponent,
  },
  {
    path: 'vendorRegisterFinal',
    component: VendorFinalRegistrationComponent,
    canActivate: [RoleGuard],
  },
  {
    path: 'vendorDashboard',
    component: VendorDashboardComponent,
  },
  {
    path: 'home2',
    component: HomeComponent,
  },
  {
    path: 'otpVerify',
    component: OtpVerifyComponent,
    canActivate: [OtpGuard],
  },
  {
    path: 'vendorOtpVerify',
    component: VendorOtpVerifyComponent,
    canActivate: [OtpGuard],
  },
  {
    path: 'forgotPassword',
    component: ForgetPasswordComponent,
    canActivate: [UnAuthGuard],
  },
  {
    path: 'resetPassword',
    component: ResetPasswordComponent,
    canActivate: [ResetpasswordGuard],
  },
  {
    path: 'waitingPage',
    component: WaitingPageComponent,
  },
  {
    path: 'blockedPage',
    component: BlockedComponent,
  },
  {
    path: 'cart',
    component: CartComponent,
  },

  {
    path: 'vendor',
    loadChildren: () =>
      import('./features/vendor/vendor.module').then((m) => m.VendorModule),
    canActivate: [VendorGuard],
  },
  {
    path: '',
    loadChildren: () =>
      import('./features/pos/pos.module').then((m) => m.PosModule),
    // canActivate: [VendorGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
