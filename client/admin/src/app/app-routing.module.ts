import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminLoginComponent } from './features/authentication/admin-login/admin-login.component';
import { ResetPasswordComponent } from './features/user-management/components/reset-password/reset-password.component';
import { ResetpasswordGuard } from './guards/resetpassword.guard';
import { UnAuthGuard } from './guards/un-auth.guard';
import { ForgetPasswordComponent } from './features/user-management/components/forget-password/forget-password.component';
import { AdminDashboardComponent } from './core/components/admin-dashboard/admin-dashboard.component';
import { AuthGuard } from './auth.guard';
import { ErrorPageComponent } from './core/components/error-page/error-page.component';

const routes: Routes = [
  {
    path: '',
    component: AdminLoginComponent,
    pathMatch: 'full',
    canActivate: [UnAuthGuard],
  },

  {
    path: 'login',
    component: AdminLoginComponent, 
    canActivate: [UnAuthGuard],
  },

  {
    path: 'forgotPassword',
    component: ForgetPasswordComponent,
  },
  {
    path: 'resetPassword',
    component: ResetPasswordComponent,
    canActivate: [ResetpasswordGuard],
  },
  {
    path: 'error',
    component: ErrorPageComponent,
    // canActivate: [ResetpasswordGuard],
  },


  {
    path: 'admin',
    loadChildren: () =>
      import('./features/features.module').then((m) => m.FeaturesModule),
      canActivate: [AuthGuard],
  },
 
  // {
  //   path: 'CategoryTEst',
  //   component: CategoryListComponent,
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
