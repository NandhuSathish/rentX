import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './components/nav/nav.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CoreRoutingModule } from './core-routing.module';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [
    NavComponent,
    AdminDashboardComponent,
    SidebarComponent,
    ErrorPageComponent,
  ],
  imports: [CommonModule, CoreRoutingModule, MatTooltipModule],
  exports: [NavComponent, SidebarComponent],
})
export class CoreModule {}
