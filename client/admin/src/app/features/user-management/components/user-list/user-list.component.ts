import { Component, NgZone, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, Subscription, debounceTime } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';

import { Router } from '@angular/router';
import { VendorService } from 'src/app/features/vendor-management/Services/vendor.service';
import { AlertService } from 'src/app/shared/services/alert.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  errorData: any;
  Responsedata: any;
  searchQuery: any = '';
  currentPage = 1;
  responseLength!: number;
  Data = { lastPage: 0 };
  sortValue: any = 'updatedAt';
  eventSubscription: Subscription;
  sortDirection: 'ASC' | 'DESC' = 'DESC';
  isConfirm = false;
  itemCount: any;

  private searchSubject = new Subject<string>();

  VendorSeachForm: FormGroup = new FormGroup({
    search: new FormControl('', Validators.required),
  });

  constructor(
    private vendorService: VendorService,
    public toast: ToastService,
    private httpClient: HttpClient,
    public eventEmitter: EventEmitterService,
    public alertService: AlertService,
    public route: Router,
    private _ngZone: NgZone
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
      this.Search(searchTerm);
    });
  }

  loadData() {
    this.vendorService
      .ListUser(
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
  clearSearch() {
    const inputElement = document.querySelector('input[name="searchField"]') as HTMLInputElement;
    if (inputElement) {
      inputElement.value = '';
      this.searchSubject.next('');
    }
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
    // if (searchTerm.trim().length === 0 || searchTerm[0] === ' ') {
    //   // No characters in the search term or first character is whitespace
    //   if (searchTerm.length == 0) {
    //     searchTerm = encodeURIComponent(searchTerm);
    //     this.vendorService
    //       .ListUser(searchTerm, this.sortValue, 1, this.sortDirection)
    //       .subscribe({
    //         next: (res: any) => {
    //           this.currentPage = res.currentPage;
    //           this.Data = res;
    //           this.Responsedata = res.result;
    //           this.responseLength = res.numItems;
    //         },
    //         error: () => {},
    //       });
    //   }
    //   return;
    // }
    this.searchQuery = searchTerm.trim();
    if (this.searchQuery != '') {
      this.searchQuery = encodeURIComponent(this.searchQuery);
      this.vendorService
        .ListUser(this.searchQuery, this.sortValue, 1, this.sortDirection)
        .subscribe({
          next: (res: any) => {
            this.currentPage = res.currentPage;
            this.Data = res;
            this.Responsedata = res.result;
            this.responseLength = res.numItems;
          },
          error: () => {},
        });
    } else {
      this.loadData();
    }
  }

  Sort(data: any) {
    this.sortValue = data;
    this.sortDirection = this.toggleSortDirection(this.sortDirection);
    this.vendorService
      .ListUser(this.searchQuery, this.sortValue, 1, this.sortDirection)
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
        .openAlert('Do you want to activate this user ?')
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
        .openAlert('Do you want to delete this user?')
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
        .openAlert('Do you want to block this user?')
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
    this.vendorService.userHandle(id, btnValue).subscribe({
      next: (res) => {
        if (btnValue === 0) {
          this.eventEmitter.onSaveEvent();
          this.isConfirm = false;
          this.toast.showSucessToast('User Activated', 'close');
        }
        if (btnValue === 1) {
          this.eventEmitter.onSaveEvent();
          this.isConfirm = false;
          this.toast.showWarningToast('User Blocked', 'close');
        }

        if (btnValue === 2) {
          this.isConfirm = false;
          this.eventEmitter.onSaveEvent();
          if (this.itemCount == 1) {
            this.currentPage = this.currentPage - 1;
            this.loadData();
          }
          this.toast.showDeleteToast('User Deleted', 'close');
        }
        this.Responsedata = res.result;
      },
    });
  }
}
