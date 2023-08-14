import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css'],
})
export class EditCategoryComponent implements OnInit, OnDestroy {
  formSubmitted = false;
  categories: any[] = [];
  pattern = '^[a-zA-Z0-9]+(?:[ ][a-zA-Z0-9]+)*$';
  minLength = 3;
  updateCategorySubscription!: Subscription;
  maxLength = 20;
  showErrorMessage = false;
  errorData: any;
  updateCategoryForm!: FormGroup;
  categoryName: any;
  constructor(
    private eventEmitter: EventEmitterService,
    private categoryService: CategoryService,
    private route: Router,
    private dialog: MatDialog,
    public toast: ToastService,
    private dialogRef: MatDialogRef<EditCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { categoryId: number }
  ) {}
  ngOnDestroy(): void {
    this.dialog.closeAll();
    this.dialogRef.close();
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
    this.getCategoryName(this.categoryId);
    this.updateCategoryForm.get('name')?.setValue(this.categoryName);
  }

  get categoryId(): number {
    return this.data.categoryId;
  }
  closeDialog() {
    if (this.updateCategoryForm) {
      this.updateCategoryForm.reset();
    }
    this.formSubmitted = false;
    this.dialog.closeAll();
    this.dialogRef.close();
  }
  handleInputChange() {
    this.showErrorMessage = false; // reset flag on input change
  }
  onSubmit() {
    this.formSubmitted = true;
    if (this.updateCategoryForm.valid) {
      this.updateCategorySubscription = this.categoryService
        .updateCategory(this.categoryId, this.updateCategoryForm.value)
        .subscribe({
          next: (result: any) => {
            this.dialog.closeAll();
            this.eventEmitter.onSaveEvent();
            this.toast.showSucessToast(
              'Category updated Successfully',
              'close'
            );
          },
          error: (error: any) => {
            this.showErrorMessage = true;
            this.errorData = error.error.errorMessage;
            this.errorData =
              this.errorData.charAt(0).toUpperCase() +
              this.errorData.slice(1).toLowerCase();
          },
        });
    }
  }

  getCategoryName(id: any) {
    this.categoryService.getCategoryName(id).subscribe({
      next: (result: any) => {
        this.categories = result;
        this.categoryName = result.result[0].category;
      },
      error: (error: any) => {
        this.errorData = error.error.errorMessage;
        this.errorData =
          this.errorData.charAt(0).toUpperCase() +
          this.errorData.slice(1).toLowerCase();
        this.toast.showErrorToast(this.errorData, 'close');
      },
    });
  }
}
