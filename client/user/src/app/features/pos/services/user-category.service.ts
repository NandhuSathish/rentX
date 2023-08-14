import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';
import { Category } from 'src/app/models/category.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserCategoryService {
  private apiURL = environment.apiUrl;
  private categories: Category[] = [];
  constructor(private httpClient: HttpClient) {}

  listAllCategories(): Observable<any> {
    // if (this.categories.length) {
    //   return of(this.categories);
    // }
    return this.httpClient
      .get<any>(`${this.apiURL}/unAuthorized/category/findAll`)
      .pipe(
        tap((res) => {
          this.categories = res.result;
        })
      );
  }
}
