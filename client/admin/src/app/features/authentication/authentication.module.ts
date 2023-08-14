import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [AdminLoginComponent],
  imports: [CommonModule, FormsModule,RouterModule,FormsModule, ReactiveFormsModule, CoreModule,],
  exports: [AdminLoginComponent],
})
export class AuthenticationModule {}
