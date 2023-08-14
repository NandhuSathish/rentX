import { Component, NgZone, OnInit } from '@angular/core';
import { VendorService } from '../../Services/vendor.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { Subject, Subscription, debounceTime } from 'rxjs';
import { NgxUiLoaderService, NgxUiLoaderConfig } from 'ngx-ui-loader';
import { AlertService } from 'src/app/shared/services/alert.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pending-vendor-list',
  templateUrl: './pending-vendor-list.component.html',
  styleUrls: ['./pending-vendor-list.component.css'],
})
export class PendingVendorListComponent implements OnInit {
  errorData: any;
  Responsedata: any;
  currentPage = 1;
  itemCount: any;
  responseLength!: number;
  searchQuery: any = '';
  Data = { lastPage: 0 };
  sortValue = 'id';
  eventSubscription: Subscription;
  isConfirm = false;
  sortDirection: 'ASC' | 'DESC' = 'DESC';
  private searchSubject = new Subject<string>();

  PendingSeachForm: FormGroup = new FormGroup({
    search: new FormControl('', Validators.required),
  });

  constructor(
    private httpClient: HttpClient,
    private vendorService: VendorService,
    public toast: ToastService,
    public eventEmitter: EventEmitterService,
    public route: Router,
    private _ngZone: NgZone,
    private ngxLoader: NgxUiLoaderService,
    public alertService: AlertService
  ) {
    this.eventSubscription = this.eventEmitter
      .getonSaveEvent()
      .subscribe(() => {
        this.loadData();
      });
  }

  ngOnInit(): void {
    this.loadData();
    this.searchSubject.pipe(debounceTime(500)).subscribe((searchTerm) => {
      // Perform search here
      this.Search(searchTerm);
    });
  }

  loadData() {
    this.vendorService
      .PendingVendors(
        this.searchQuery,
        this.sortValue,
        this.currentPage,
        this.sortDirection
      )
      .subscribe({
        next: (res) => {
          this.Data = res;
          this.Responsedata = res.result;
          this.itemCount = res.result.length;
          this.responseLength = res.numItems;
        },
        error: (error) => {
          this.errorData = error;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
        },
      });
  }
  clearSearch() {
    const inputElement = document.querySelector('input[name="searchField"]') as HTMLInputElement;
    if (inputElement) {
      inputElement.value = '';
      this.searchSubject.next('');
    }
  }
  
  
  onSearch(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    this.searchSubject.next(searchTerm);
  }

  Search(searchTerm: string) {
    this.searchQuery = searchTerm;
    if(searchTerm!==''){
      searchTerm = encodeURIComponent(searchTerm);
    searchTerm = encodeURIComponent(searchTerm);
    this.vendorService
      .PendingVendors(searchTerm, this.sortValue, 1, this.sortDirection)
      .subscribe({
        next: (res: any) => {
          this.currentPage = res.currentPage;
          this.Data = res;
          this.Responsedata = res.result;
          this.responseLength = res.numItems;
        },
        error: () => {},
      });
  } else{
    this.loadData();
  }
}

  toggleSortDirection(sortDirection: 'ASC' | 'DESC'): 'ASC' | 'DESC' {
    return this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
  }
  Sort(data: any) {
    this.sortValue = data;
    this.sortDirection = this.toggleSortDirection(this.sortDirection);
    this.vendorService
      .PendingVendors(this.searchQuery, this.sortValue, 1, this.sortDirection)
      .subscribe({
        next: (res) => {
          this.Data = res;
          this.Responsedata = res.result;
          this.currentPage = res.currentPage;
        },
      });
  }

  requestHandle(btnValue: any, pendingVendor: any) {
    if (btnValue == 0) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to accept the request?')
        .subscribe((res) => {
          if (res) {
            this.approveOrReject(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    } else if ((btnValue = 1)) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to reject the request?')
        .subscribe((res) => {
          if (res) {
            this.approveOrReject(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    }
  }

  Pagenate(pageNo: any) {
    this.currentPage = pageNo;
    this.loadData();
  }

  approveOrReject(id: any, btnValue: any) {
    this.vendorService.vendorRequestHandle(id, btnValue).subscribe({
      next: (res) => {
        if (btnValue === 0) {
          this.eventEmitter.onSaveEvent();
          this.isConfirm = false;
          if (this.itemCount == 1) {
            this.currentPage = this.currentPage - 1;
            this.loadData();
          }
          this.toast.showSucessToast('Approved', 'close');
        }
        if (btnValue === 1) {
          this.isConfirm = false;
          this.eventEmitter.onSaveEvent();
          if (this.itemCount == 1) {
            this.currentPage = this.currentPage - 1;
            this.loadData();
          }
          this.toast.showDeleteToast('Rejected', 'close');
        }
        this.Responsedata = res.result;
      },
    });
  }
}
