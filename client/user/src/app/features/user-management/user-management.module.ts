import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'src/app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';

import { LoginComponent } from './components/login/login.component';
import { UserRegistraionComponent } from './components/user-registraion/user-registraion.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';

import { ResetPasswordComponent } from './components/reset-password/reset-password.component';

import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import { VendorInitialRegistrationComponent } from './components/vendor-registration/vendor-initial-registration/vendor-initial-registration.component';
import { VendorFinalRegistrationComponent } from './components/vendor-registration/vendor-final-registration/vendor-final-registration.component';
import { MatInputModule } from '@angular/material/input';
import { VendorOtpVerifyComponent } from './components/vendor-otp-verify/vendor-otp-verify.component';
import { ForgetPasswordComponent } from './components/forget-password/forget-password.component';
import { WaitingPageComponent } from './components/waiting-page/waiting-page.component';
import { NgxUiLoaderModule } from 'ngx-ui-loader';

@NgModule({
  declarations: [
    LoginComponent,
    UserRegistraionComponent,
    ForgetPasswordComponent,
    ResetPasswordComponent,
    VendorInitialRegistrationComponent,
    VendorFinalRegistrationComponent,
    VendorOtpVerifyComponent,
    WaitingPageComponent,
  ],
  imports: [
    SharedModule,
    CoreModule,
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatIconModule,
    MatStepperModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    NgxUiLoaderModule,
  ],
  providers: [],
  exports: [
    UserRegistraionComponent,
    LoginComponent,
    ForgetPasswordComponent,
    ResetPasswordComponent,
    VendorInitialRegistrationComponent,
    VendorFinalRegistrationComponent,
    VendorOtpVerifyComponent,
  ],
})
export class UserManagementModule {}
