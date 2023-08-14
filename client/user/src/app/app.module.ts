import { NgModule, importProvidersFrom } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FeaturesModule } from './features/features.module';
import { CoreModule } from './core/core.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptorService } from './auth.interceptor';
import {
  NgxUiLoaderModule,
  NgxUiLoaderConfig,
  SPINNER,
  POSITION,
  PB_DIRECTION,
} from 'ngx-ui-loader';
import { NgxImageCompressService } from 'ngx-image-compress';
import { ClickOutsideDirective } from './directives/click-outside.directive';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: '#3b82f6',
  bgsOpacity: 0.4,
  bgsPosition: 'bottom-right',
  bgsSize: 60,
  bgsType: 'ball-spin-clockwise',
  blur: 8,
  delay: 0,
  fastFadeOut: true,
  fgsColor: '#3b82f6',
  fgsPosition: 'center-center',
  fgsSize: 60,
  fgsType: 'square-jelly-box',
  gap: 24,
  logoPosition: 'center-center',
  logoSize: 120,
  logoUrl: '',
  masterLoaderId: 'master',
  overlayBorderRadius: '0',
  overlayColor: 'rgba(56,56,56,0.8)',
  pbColor: '#3b82f6',
  pbDirection: 'ltr',
  pbThickness: 2,
  hasProgressBar: true,
  text: '',
  textColor: '#FFFFFF',
  textPosition: 'center-center',
  maxTime: -1,
  minTime: 300,
};

@NgModule({
  declarations: [AppComponent, ClickOutsideDirective],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FeaturesModule,
    CoreModule,
    HttpClientModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
      
    },NgxImageCompressService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
