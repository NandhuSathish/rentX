import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from 'src/app/models/store.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CartServicesService {
  private apiURL = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  // addStores
  addToCart(ProductDetails: any): Observable<Store> {
    return this.httpClient.post(
      `${this.apiURL}/user/cart/add`,
      ProductDetails
    );
  }
}