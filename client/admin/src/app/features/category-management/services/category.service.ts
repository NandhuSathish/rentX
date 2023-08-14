import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiURL = environment.apiUrl;
  constructor(
    private httpClient: HttpClient,
    private _ngZone: NgZone,
    private router: Router
  ) {}

  addCategory(data: any): Observable<any> {
    console.log(data)
    return this.httpClient.post(`${this.apiURL}/category`, data);
  }
  addSubCategory(paramBody: any) {
    return this.httpClient.post(`${this.apiURL}/subcategory`, paramBody);
  }
  updateCategory(categoryId: any, data: any) {
    return this.httpClient.put(`${this.apiURL}/category/${categoryId}`, data);
  }
  updateSubCategory(subCategoryId: number, data: any) {
    return this.httpClient.put(
      `${this.apiURL}/subcategory/${subCategoryId}`,
      data
    );
  }

  getCategoryName(id: any) {
    return this.httpClient.get(`${this.apiURL}/subcategory/${id}`);
  }
  getCategory(params: any) {
    return this.httpClient.get(`${this.apiURL}/category?${params}`);
  }

  getSubCategory(categoryid: any, params: any) {
    return this.httpClient.get(
      `${this.apiURL}/subcategory/${categoryid}?${params}`
    );
  }
  getSubCategorydetail(id: any) {
    return this.httpClient.get(`${this.apiURL}/subcategory/detail/${id}`);
  }
  deleteCategory(id: number) {
    return this.httpClient.put(`${this.apiURL}/category/delete/${id}`, id);
  }
  deleteSubCategory(subCategoryId: number) {
    return this.httpClient.put(
      `${this.apiURL}/subcategory/delete/${subCategoryId}`,
      subCategoryId
    );
  }
}
