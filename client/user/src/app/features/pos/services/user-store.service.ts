import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';
import { Store } from 'src/app/models/store.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserStoreService {
  private apiURL = environment.apiUrl;
  private stores: Store[] = [];
  constructor(private httpClient: HttpClient) {}
  listAllStores(): Observable<any> {
    // if (this.stores.length) {
    //   return of(this.stores);
    // }
    return this.httpClient
      .get<any>(`${this.apiURL}/unAuthorized/store/findAll`)
      .pipe(
        tap((res) => {
          this.stores = res.result;
        })
      );
  }
}
