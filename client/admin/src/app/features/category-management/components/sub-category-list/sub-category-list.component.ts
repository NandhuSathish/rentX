import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryModalService } from '../../services/category-modal.service';
import { CategoryService } from '../../services/category.service';
import { Subject, Subscription, debounceTime } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-sub-category-list',
  templateUrl: './sub-category-list.component.html',
  styleUrls: ['./sub-category-list.component.css'],
})
export class SubCategoryListComponent implements OnInit, OnDestroy {
  searchControl!: FormControl;
  searchForm!: FormGroup;
  subcategories: any[] = [];
  isDisabled = true;
  pattern = '^[a-zA-Z0-9]+(?:[ ][a-zA-Z0-9]+)*$';
  minLength = 3;
  maxLength = 20;
  itemCount: any;
  errorData: any;
  private searchSubject = new Subject<string>();
  updateCategoryForm!: FormGroup;
  eventSubscription!: Subscription;
  categoryid: any;
  responseLength: any;
  categoryName: any;
  lastPage!: number;
  currentPage: number = 1;
  constructor(
    private dialog: MatDialog,
    private eventEmitter: EventEmitterService,
    private activatedRoute: ActivatedRoute,
    public ModalService: CategoryModalService,
    private categoryService: CategoryService,
    public toast: ToastService,
    private router: Router
  ) {
    this.searchControl = new FormControl();
    this.searchForm = new FormGroup({
      searchInput: this.searchControl,
    });
  }

  ngOnInit(): void {
    this.updateCategoryForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.pattern(this.pattern),
        Validators.minLength(this.minLength),
        Validators.maxLength(this.maxLength),
      ]),
    });

    this.categoryid = this.activatedRoute.snapshot.paramMap.get('categoryid');

    this.GetSubCategory(this.categoryid);
    this.eventSubscription = this.eventEmitter
      .getonDeleteEvent()
      .subscribe(() => {
        if (this.itemCount == 1 && this.currentPage > 1) {
          this.previousPage();
        }
      });
    this.eventSubscription = this.eventEmitter
      .getonSaveEvent()
      .subscribe(() => {
        this.GetSubCategory(this.categoryid);
      });
    this.searchSubject.pipe(debounceTime(500)).subscribe((searchTerm) => {
      // Perform search here
      this.Search(searchTerm);
    });
  }

  page = this.currentPage;
  params = {
    search: '',
    page: this.page,
    size: '7',
    sort: 'updatedAt',
    direction: 'DESC',
  };
  GetSubCategory(categoryid: any) {
    const queryString = Object.entries(this.params)
      .map(([key, value]) => `${key}=${value}`)
      .join('&');
    this.categoryService.getSubCategory(categoryid, queryString).subscribe({
      next: (result: any) => {
        this.categoryName = result.result[0].category;
        this.lastPage = result.lastPage;
        this.currentPage = result.currentPage;
        this.responseLength = result.numItems;
        this.updateCategoryForm.controls['name'].setValue(this.categoryName);

        this.subcategories = result.result[0].subcategories;
        this.itemCount = this.subcategories.length;
      },
      error: (error: any) => {
        this.errorData = error.error.errorMessage;
        let errorCode = error.error.errorCode;
        if (
          errorCode == 3014 ||
          errorCode == 3013 ||
          errorCode == 3062 ||
          errorCode == 1931
        ) {
          this.router.navigateByUrl('/error');
        } else {
          if (this.errorData) {
            this.errorData =
              this.errorData.charAt(0).toUpperCase() +
              this.errorData.slice(1).toLowerCase();
            this.toast.showErrorToast(this.errorData, 'close');
          }
        }
      },
    });
  }

  addSubCategory() {
    this.ModalService.addSubCategory(this.categoryid).subscribe();
    this.params.sort = 'updatedAt';
    this.params.direction = 'DESC';
  }

  editSubCategory(id: any) {
    this.ModalService.editSubCategory(id).subscribe();
  }
  deleteSubCategory(id: any) {
    this.ModalService.deleteSubCategory(id).subscribe();
  }

  clearSearch() {
    this.searchForm.reset();
    this.params.search = '';
    this.params.page = 1;
    this.GetSubCategory(this.categoryid);
  }
  ngOnDestroy(): void {
    this.eventSubscription.unsubscribe();
  }

  onKeyUp(event: Event): void {
    const searchTerm = (event.target as HTMLInputElement).value;
    this.searchSubject.next(searchTerm);
  }

  Search(searchTerm: string) {
    if (searchTerm.trim().length === 0 || searchTerm[0] === ' ') {
      if (searchTerm.length == 0) {
        this.params.search = encodeURIComponent(searchTerm);
        this.params.page = 1;
        this.GetSubCategory(this.categoryid);
      }
      return;
    }

    this.params.search = encodeURIComponent(searchTerm);
    this.params.page = 1;
    this.GetSubCategory(this.categoryid);
  }

  nextPage() {
    this.params.page++;
    this.GetSubCategory(this.categoryid);
  }
  previousPage() {
    this.params.page--;
    this.GetSubCategory(this.categoryid);
  }
  toggleSortDirection() {
    // Toggle the sort direction between ASC and DESC
    this.params.sort = 'name';
    this.params.page = 1;
    this.params.direction = this.params.direction === 'ASC' ? 'DESC' : 'ASC';
    this.GetSubCategory(this.categoryid);
  }
}
