import {
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { StoreService } from '../../../services/store.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-store-list',
  templateUrl: './store-list.component.html',
  styleUrls: ['./store-list.component.css'],
})
export class StoreListComponent implements OnInit {
  @ViewChild('mobileFilterMenu') mobileFilterMenu!: ElementRef;
  eventSubscription: Subscription;
  private searchSubject = new Subject<string>();
  storeListResponse: any;
  searchQuery: string = '';
  sortField = 'id';
  sortOrder: boolean = false;
  sortDirection: 'ASC' | 'DESC' = 'DESC';
  pageNo = 1;
  isLoading = false;
  responseLength: any;
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
    private storeService: StoreService
  ) {
    this.eventSubscription = this.eventEmitter
      .getonSaveEvent()
      .subscribe(() => {
        this.loadData();
      });
  }

  loadData() {
    this.storeService.listAllStores(this.pageNo).subscribe((res) => {
      this.storeListResponse = res;
      this.responseLength = res.numItems;
    });
  }

  // sort direction toggle.
  toggleSortDirection(sortDirection: 'ASC' | 'DESC'): 'ASC' | 'DESC' {
    return this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
  }

  sort(sortField: string) {
    this.sortField = sortField;
    this.sortDirection = this.toggleSortDirection(this.sortDirection);
    this.storeService
      .listAllStores(
        undefined,
        undefined,
        this.sortField,
        this.sortDirection,
        this.searchQuery
      )
      .subscribe({
        next: (res: any) => {
          this.storeListResponse = res;
          this.responseLength = res.numItems;
          this.pageNo = 1;
        },
        error: () => {},
      });
  }

  // onSearchEventListner
  onSearch(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    this.searchSubject.next(searchTerm);
  }

  clearSearch() {
    const inputElement = document.querySelector('input[name="searchField"]') as HTMLInputElement;
    if (inputElement) {
      inputElement.value = '';
      this.searchSubject.next('');
    }
  }
  // Search function.
  Search(searchTerm: string) {
    // if(searchTerm.trim()!=''){
    if (searchTerm.trim().length === 0 || searchTerm[0] === ' ') {
      // No characters in the search term or first character is whitespace
      if (searchTerm.length == 0) {
        searchTerm = encodeURIComponent(searchTerm);
        this.searchQuery = searchTerm;
        this.storeService
          .listAllStores(undefined, undefined, undefined, undefined, searchTerm)
          .subscribe({
            next: (res: any) => {
              this.storeListResponse = res;
              this.responseLength = res.numItems;
              this.pageNo = res.currentPage;
            },
            error: () => {},
          });
      }
      return;
    }
    searchTerm = encodeURIComponent(searchTerm);
    this.searchQuery = searchTerm;
    this.storeService
      .listAllStores(undefined, undefined, undefined, undefined, searchTerm)
      .subscribe({
        next: (res: any) => {
          this.storeListResponse = res;
          this.responseLength = res.numItems;
          this.pageNo = res.currentPage;
        },
        error: () => {},
      });
  }

  // Pagination controls.
  onNext() {
    this.isLoading = true;
    this.pageNo++;
    let params = {
      pageNo: this.pageNo,
      sortField: this.sortField,
      sortDirection: this.sortDirection,
      searchQuery: this.searchQuery,
    };
    setTimeout(() => {
      this.storeService
        .listAllStores(
          params.pageNo,
          undefined,
          params.sortField,
          params.sortDirection,
          params.searchQuery
        )
        .subscribe({
          next: (res: any) => {
            this.isLoading = false;
            this.storeListResponse = res;
            this.responseLength = res.numItems;
          },
          error: () => {},
        });
    }, 20);
  }

  onPrevious() {
    this.isLoading = true;
    this.pageNo--;
    let params = {
      pageNo: this.pageNo,
      sortField: this.sortField,
      sortDirection: this.sortDirection,
      searchQuery: this.searchQuery,
    };
    setTimeout(() => {
      this.storeService
        .listAllStores(
          params.pageNo,
          undefined,
          params.sortField,
          params.sortDirection,
          params.searchQuery
        )
        .subscribe({
          next: (res: any) => {
            this.isLoading = false;
            this.storeListResponse = res;
            this.responseLength = res.numItems;
          },
          error: () => {},
        });
    }, 20);
  }
}
