import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private apiURL = environment.apiUrl;
  constructor(
    private httpClient: HttpClient,
    private _ngZone: NgZone,
    private router: Router
  ) {}

  ListVendors(
    data: any,
    sort: any,
    pageNo: any,
    sortDirection: any
  ): Observable<any> {
    return this.httpClient.get(
      `${this.apiURL}/admin/vendor/list?searchData=` +
        data +
        `&order=` +
        sortDirection +
        `&page=` +
        pageNo +
        `&limit=7&statusValue=1&sort=` +
        sort
    );
  }

  ListUser(
    data: any,
    sort: any,
    pageNo: any,
    sortDirection: any
  ): Observable<any> {
    return this.httpClient.get(
      `${this.apiURL}/admin/user/list?search=` +
        data +
        `&order=` +
        sortDirection +
        `&page=` +
        pageNo +
        `&limit=7&statusValue=1&sort=` +
        sort
    );
  }

  PendingVendors(
    data: any,
    sort: any,
    pageNo: any,
    sortDirection: any
  ): Observable<any> {
    return this.httpClient.get(
      `${this.apiURL}/admin/vendor/list?searchData=${encodeURIComponent(
        data
      )}&order=${sortDirection}&page=${pageNo}&limit=7&statusValue=0&sort=${sort}`
    );
  }
  vendorRequestHandle(Vendor: any, data: any): Observable<any> {
    return this.httpClient.put(
      `${this.apiURL}/admin/vendor/approve/` + Vendor + `?btnValue=` + data,
      null
    );
  }
  userHandle(Vendor: any, data: any): Observable<any> {
    return this.httpClient.put(
      `${this.apiURL}/admin/user/manageUser/` + Vendor + `?btnValue=` + data,
      null
    );
  }
}
