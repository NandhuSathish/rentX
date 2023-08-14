import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Store } from 'src/app/models/store.model';

@Injectable({
  providedIn: 'root',
})
export class StoreService {
  private apiURL = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  // addStores
  addStoreDetails(StoreDetails: any): Observable<Store> {
    return this.httpClient.post<Store>(
      `${this.apiURL}/vendor/store/add`,
      StoreDetails
    );
  }

  listAllStores(
    pageNo = 1,
    pageSize = 7,
    sortField = 'updatedAt',
    sortDirection = 'DESC',
    searchQuery = ''
  ): Observable<any> {
    return this.httpClient.get<any>(
      `${this.apiURL}/vendor/store/list?search=${searchQuery}&page=${pageNo}&size=${pageSize}&sort=${sortField}&direction=${sortDirection}`
    );
  }
}
