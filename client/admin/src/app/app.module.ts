import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FeaturesModule } from './features/features.module';
import { RouterModule } from '@angular/router';
import { TokenInterceptorService } from './shared/services/token-intercepter.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxUiLoaderConfig, NgxUiLoaderModule } from 'ngx-ui-loader';
const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: '#ffffff', // Background color of the progress bar
  bgsOpacity: 0.8, // Opacity of the progress bar
  pbColor: '#007bff', // Color of the progress bar
  pbThickness: 3, // Thickness of the progress bar
  overlayColor: '#000000', // Color of the overlay
  // overlayOpacity: 0.5, // Opacity of the overlay
  hasProgressBar: true, // Show only the progress bar
  text: '', // No text to display
};
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FeaturesModule,
    RouterModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
