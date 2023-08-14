import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartStateService {
  private cartCountSubject = new BehaviorSubject<any>(localStorage.getItem('cartCount'));
  cartCount$ = this.cartCountSubject.asObservable();

  updateCartCount(count: any) {
    this.cartCountSubject.next(count)
  }
}