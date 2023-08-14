import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserProductService {
  private apiURL = environment.apiUrl;
  private productList: any = [];
  private lastPage = 'lastPage';
  public currentPage: any;
  private product = {
    id: 1,
    name: 'amalu',
    description: 'buahhahaha',
    specification: {
      colour: 'gray',
      weight: '65 KG',
    },
    stock: 13,
    availableStock: 1,
    price: 10000.0,
    images: [
      {
        image1:
          'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2F1686054523?alt=media&token=0e969468-1d20-4077-9115-a4e7dfeba540',

        image2:
          'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2F31PIJcknlCS._SS400_.jpg?alt=media&token=ab23a8ee-6a82-4910-a890-22a123578bc2',

        image3:
          'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2Fpexels-kate-amos-5952015.jpg?alt=media&token=f93c3460-e52b-4db6-8afa-1f6054f611b2',

        image4:
          'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2Fpexels-kate-amos-2718447%20(1)%20Cropped.jpg?alt=media&token=261600f0-4960-44b7-8769-7560f912aac8',

        image5:
          'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2Fdoors.jpeg?alt=media&token=d646792b-28ce-4a47-a01f-6aa4eee8f1f0',
      },
    ],
    categoryId: 12,
    categoryName: 'Botawtfslqfwe7y',
    subCategoryId: 1,
    subCategoryName: 'Bottle',
    userId: 29,
    userName: 'sdfsfdsf',
    storeId: 1,
    storeName: '@@@@@*',
  };

  constructor(private httpClient: HttpClient, private router: Router) {}

  setlastPage(value: boolean) {
    localStorage.setItem(this.lastPage, value.toString());
  }

  // list out the products .
  listProduct(
    pageNo = 1,
    pageSize = 15,
    sortField = 'updatedAt',
    sortDirection = 'DESC',
    searchQuery = '',
    categoryFilter = '',
    storeFilter = ''
  ): Observable<any> {
    let searchTerm = encodeURIComponent(searchQuery);
    return this.httpClient
      .get<any>(
        `${this.apiURL}/user/product/list?search=${searchQuery}&page=${pageNo}&size=${pageSize}&sort=${sortField}&order=${sortDirection}&category=${categoryFilter}&store=${storeFilter}`
      )
      .pipe(
        tap((res) => {
          this.productList = res.data;
          this.setlastPage(res.lastPage);
        })
      );
  }

  getProductById(id: number): Observable<any> {
    if (this.product) {
      return of(this.product);
    }
    return this.httpClient.get<any>(`${this.apiURL}/user/product/${id}`);
  }

  // searchProduct(
  //   pageNo = 1,
  //   pageSize = 15,
  //   sortField = 'updatedAt',
  //   sortDirection = 'DESC',
  //   searchQuery = '',
  //   categoryFilter = '',
  //   storeFilter = ''
  // ): Observable<any> {
  //   // const params = new HttpParams().set('search', searchQuery);
  //   let searchTerm = encodeURIComponent(searchQuery);
  //   console.log(searchQuery, 'searchQuery');
  //   console.log(searchTerm, 'searchTerm');

  //   return this.httpClient
  //     .get<any>(
  //       `${this.apiURL}/user/product/list?search=${searchTerm}&page=${pageNo}&size=${pageSize}&sort=${sortField}&order=${sortDirection}&category=${categoryFilter}&store=${storeFilter}`
  //     )
  //     .pipe(
  //       tap((res) => {
  //         this.productList = res.data;

  //         this.router.navigate(['/products'], {
  //           queryParams: {
  //             search: searchQuery,
  //           },
  //         });
  //       })
  //     );
  // }
}
