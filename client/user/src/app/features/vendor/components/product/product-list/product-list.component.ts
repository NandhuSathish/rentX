import {
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { AlertService } from 'src/app/shared/services/alert.service';
import { ProductService } from '../../../services/product.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  @ViewChild('mobileFilterMenu') mobileFilterMenu!: ElementRef;
  eventSubscription: Subscription;
  private searchSubject = new Subject<string>();
  productListResponse: any;
  searchQuery: string = '';
  sortField = 'updatedAt';
  selectedCategory: any = null;
  Data = { lastPage: 0 };
  sortOrder: boolean = false;
  sortDirection: 'ASC' | 'DESC' = 'DESC';
  pageNo = 1;
  categoryList: any;
  isConfirm = false;
  responseLength: any;
  isLoading: boolean = false;
  itemCount: any;
  currentPage = 1;

  Responsedata: any;
  productList: any;
  ngOnInit(): void {
    this.loadData();
    this.searchSubject.pipe(debounceTime(500)).subscribe((searchTerm) => {
      // Perform search here
      this.Search(searchTerm);
    });
  }

  constructor(
    private renderer: Renderer2,
    private eventEmitter: EventEmitterService,
    public toast: ToastService,
    private productService: ProductService,
    public alertService: AlertService
  ) {
    this.eventSubscription = this.eventEmitter
      .getonSaveEvent()
      .subscribe(() => {
        this.loadData();
      });
  }
  loadData() {
    this.getAllCategory();
    this.productService
      .listAllProducts(
        this.searchQuery,
        this.sortField,
        this.sortDirection,
        this.currentPage
      )
      .subscribe((res) => {
        this.Data = res;
        this.productList = res.result;
        console.log(this.productList);
        this.itemCount = res.result.length;
        this.responseLength = res.numItems;
      });
  }

  // sort direction toggle.
  toggleSortDirection(sortDirection: 'ASC' | 'DESC'): 'ASC' | 'DESC' {
    return this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
  }
  onCategoryChange() {
    this.productService
      .listAllProducts(
        this.searchQuery,
        this.sortField,
        this.sortDirection,
        1,
        this.selectedCategory
      )
      .subscribe((res) => {
        this.Data = res;
        this.productList = res.result;
        console.log(this.productList);
        this.itemCount = res.result.length;
        this.currentPage = 1;
        this.responseLength = res.numItems;
      });
    // Perform any actions based on the selected category ID
  }
  getAllCategory() {
    this.productService.getCategory().subscribe({
      next: (data: any) => {
        this.categoryList = data;
        console.log('Category = ', this.categoryList);
      },
      error: () => {},
    });
  }
  requestHandle(product: any) {
    this.isConfirm = true;
    this.alertService
      .openAlert('Do you want to delete the product?')
      .subscribe((res) => {
        if (res) {
          this.deleteProduct(product.id);
        } else {
          this.isConfirm = false;
          return;
        }
      });
  }
  detailViewNavigation(productId: any) {}

  deleteProduct(id: any) {
    this.productService.deleteProduct(id).subscribe({
      next: (res) => {
        this.eventEmitter.onSaveEvent();
        this.isConfirm = false;
        if (this.itemCount == 1) {
          this.currentPage = this.currentPage - 1;
          this.loadData();
        }
        this.toast.showDeleteToast('Product Deleted', 'close');
      },
    });
  }
  sort(sortField: string) {
    this.sortField = sortField;
    this.sortDirection = this.toggleSortDirection(this.sortDirection);
    this.productService
      .listAllProducts(
        this.searchQuery,
        this.sortField,
        this.sortDirection,
        1,
        this.selectedCategory
      )
      .subscribe({
        next: (res: any) => {
          this.productList = res.result;
          this.responseLength = res.numItems;
          this.Data = res;
          this.currentPage = 1;
        },
        error: () => {},
      });
  }

  // onSearchEventListner
  onSearch(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    this.searchSubject.next(searchTerm);
  }

  // Search function.
  Search(searchTerm: string) {
    // if(searchTerm.trim()!=''){
    if (searchTerm.trim().length === 0 || searchTerm[0] === ' ') {
      // No characters in the search term or first character is whitespace
      if (searchTerm.length == 0) {
        searchTerm = encodeURIComponent(searchTerm);
        this.searchQuery = searchTerm;

        this.productService
          .listAllProducts(
            this.searchQuery,
            this.sortField,
            this.sortDirection,
            1,
            this.selectedCategory
          )
          .subscribe({
            next: (res: any) => {
              this.Data = res;
              this.productList = res.result;
              this.pageNo = res.currentPage;
              this.responseLength = res.numItems;
            },
            error: () => {},
          });
      }

      return;
    }

    searchTerm = encodeURIComponent(searchTerm);
    this.searchQuery = searchTerm;

    this.productService
      .listAllProducts(
        this.searchQuery,
        this.sortField,
        this.sortDirection,
        1,
        this.selectedCategory
      )
      .subscribe({
        next: (res: any) => {
          this.Data = res;
          this.productList = res.result;
          this.pageNo = res.currentPage;
          this.currentPage = res.firstPage;
          this.responseLength = res.numItems;
        },
        error: () => {},
      });
  }

  clearSearch() {
    const inputElement = document.querySelector(
      'input[name="searchField"]'
    ) as HTMLInputElement;
    if (inputElement) {
      inputElement.value = '';
      this.searchSubject.next('');
    }
  }
  Pagenate(pageNo: any) {
    this.currentPage = pageNo;
    this.isLoading = true; // Set isLoadingData to true before making the API call

    setTimeout(() => {
      this.productService
        .listAllProducts(
          this.searchQuery,
          this.sortField,
          this.sortDirection,
          this.currentPage,
          this.selectedCategory
        )
        .subscribe((res) => {
          this.Data = res;
          this.productList = res.result;
          console.log(this.productList);
          this.itemCount = res.result.length;
          this.isLoading = false;
          this.responseLength = res.numItems;
          this.isLoading = false; // Set isLoadingData to false after receiving the API response
        });
    }, 20);
  }
}
