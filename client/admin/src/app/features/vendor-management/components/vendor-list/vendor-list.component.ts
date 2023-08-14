import { Component, NgZone, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, Subscription, debounceTime } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { VendorService } from '../../Services/vendor.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/shared/services/alert.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-vendor-list',
  templateUrl: './vendor-list.component.html',
  styleUrls: ['./vendor-list.component.css'],
})
export class VendorListComponent implements OnInit {
  errorData: any;
  Responsedata: any;
  searchQuery: any = '';
  currentPage = 1;
  itemCount: any;
  responseLength!: number;
  Data = { lastPage: 0 };
  sortValue: any = 'updatedAt';
  eventSubscription: Subscription;
  sortDirection: 'ASC' | 'DESC' = 'DESC';
  isConfirm = false;
  private searchSubject = new Subject<string>();

  VendorSeachForm: FormGroup = new FormGroup({
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
      .ListVendors(
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
  toggleSortDirection(sortDirection: 'ASC' | 'DESC'): 'ASC' | 'DESC' {
    return this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
  }
  onSearch(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    this.searchSubject.next(searchTerm);
  }

  Search(searchTerm: string) {
    this.searchQuery = searchTerm;
    searchTerm=searchTerm.trim()
    if(searchTerm!==''){
    searchTerm = encodeURIComponent(searchTerm);
    this.vendorService
      .ListVendors(searchTerm, this.sortValue, 1, this.sortDirection)
      .subscribe({
        next: (res: any) => {
          this.currentPage = res.currentPage;
          this.Data = res;
          this.Responsedata = res.result;
          this.responseLength = res.numItems;
        },
        error: () => {},
      });
    }
    else{
      this.loadData();
    }
  }

  Sort(data: any) {
    this.sortValue = data;
    this.sortDirection = this.toggleSortDirection(this.sortDirection);
    this.vendorService
      .ListVendors(this.searchQuery, this.sortValue, 1, this.sortDirection)
      .subscribe({
        next: (res) => {
          this.currentPage = res.currentPage;
          this.Data = res;
          this.Responsedata = res.result;
          this.responseLength = res.numItems;
        },
      });
  }
  Pagenate(pageNo: any) {
    this.currentPage = pageNo;
    this.loadData();
  }
  requestHandle(btnValue: any, pendingVendor: any) {
    if (btnValue == 0) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to activate this Vendor?')
        .subscribe((res) => {
          if (res) {
            this.userActions(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    } else if (btnValue == 1) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to reject the request?')
        .subscribe((res) => {
          if (res) {
            this.userActions(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    } else if (btnValue == 2) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to delete this vendor?')
        .subscribe((res) => {
          if (res) {
            this.userActions(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    } else if (btnValue == 3) {
      this.isConfirm = true;
      this.alertService
        .openAlert('Do you want to block this vendor?')
        .subscribe((res) => {
          if (res) {
            this.userActions(pendingVendor.id, btnValue);
          } else {
            this.isConfirm = false;
            return;
          }
        });
    }
  }
  userActions(id: any, btnValue: any) {
    this.vendorService.vendorRequestHandle(id, btnValue).subscribe({
      next: (res) => {
        if (btnValue === 0) {
          this.eventEmitter.onSaveEvent();
          this.isConfirm = false;
          this.toast.showSucessToast('Vendor Activated', 'close');
        }
        if (btnValue === 3) {
          this.eventEmitter.onSaveEvent();
          this.isConfirm = false;
          this.toast.showWarningToast('Vendor Blocked', 'close');
        }

        if (btnValue === 2) {
          this.isConfirm = false;
          this.eventEmitter.onSaveEvent();
          if (this.itemCount == 1) {
            this.currentPage = this.currentPage - 1;
            this.loadData();
          }
          this.toast.showSucessToast('Vendor Deleted', 'close');
        }
        this.Responsedata = res.result;
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
}
