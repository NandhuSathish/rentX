import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Store } from 'src/app/models/store.model';
import { Observable } from 'rxjs';
import { Product } from 'src/app/models/product.model';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  deleteProduct(id: any) {
    console.log(`${this.apiURL}/vendor/product/delete/${id}`);
    return this.httpClient.put(
      `${this.apiURL}/vendor/product/delete/${id}`,
      null
    );
  }

  private apiURL = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  // addStores
  addProductDetails(ProductDetails: any): Observable<Store> {
    return this.httpClient.post<Product>(
      `${this.apiURL}/vendor/product/add`,
      ProductDetails
    );
  }
  getCategory(): Observable<Store> {
    return this.httpClient.get<Product>(
      `${this.apiURL}/unAuthorized/category/findAll`
    );
  }
  getStore(): Observable<Store> {
    return this.httpClient.get<Product>(
      `${this.apiURL}/unAuthorized/store/findAll`
    );
  }
  getSubcategory(id: any): Observable<Store> {
    return this.httpClient.get<Product>(
      `${this.apiURL}/unAuthorized/subCategory/${id}`
    );
  }
  getVendorProduct(id:any): Observable<Store> {
    return this.httpClient.get<Product>(
      `${this.apiURL}/vendor/product/detailView/${id}`
      // `/unAuthorized/subCategory/${id}`
    );
  }
 
  sentImages(StoreDetails: any): Observable<Store> {
    return this.httpClient.post<Store>(
      `${this.apiURL}/vendor/product/addImage`,
      StoreDetails
    );
  }
  listAllProducts(
    searchData: string = '',
    sortField: string = 'updatedAt',
    sortDirection: string = 'ASC',
    pageNo: number = 1,
    category: string = '',
    pageSize: number = 7,
   
    store: string = ''
  ): Observable<any> {
    
    const url = `${this.apiURL}/vendor/product/list?search=${searchData}&page=${pageNo}&size=${pageSize}&sort=${sortField}&order=${sortDirection}&category=${category}&store=${store}`;
  console.log(url);
    return this.httpClient.get<any>(`${this.apiURL}/vendor/product/list?search=${searchData}&page=${pageNo}&size=${pageSize}&sort=${sortField}&order=${sortDirection}&category=${category}&store=${store}`
    );
  }
  listAllStores(
    pageNo = 1,
    sortField = 'updatedAt',
    sortDirection = 'DESC',
    searchQuery = ''
  ): Observable<any> {
    return this.httpClient.get<any>(
      `${this.apiURL}/vendor/store/list?search=${searchQuery}&page=${pageNo}&size=2000000000&sort=${sortField}&direction=${sortDirection}`
    );
  }
}
