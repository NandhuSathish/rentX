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
import { EditCategoryComponent } from '../edit-category/edit-category.component';

@Component({
  selector: 'app-edit-subcategory',
  templateUrl: './edit-subcategory.component.html',
  styleUrls: ['./edit-subcategory.component.css'],
})
export class EditSubcategoryComponent implements OnInit, OnDestroy {
  formSubmitted = false;
  categories: any[] = [];
  pattern = '^[a-zA-Z0-9]+(?:[ ][a-zA-Z0-9]+)*$';
  minLength = 3;
  maxLength = 20;
  errorData: any;
  updateSubscription!: Subscription;
  getDataSubscription!: Subscription;
  showErrorMessage = false;
  updateSubCategoryForm!: FormGroup;
  subCategoryName: any;
  constructor(
    private eventEmitter: EventEmitterService,
    private categoryService: CategoryService,
    private route: Router,
    private dialog: MatDialog,
    public toast: ToastService,
    private dialogRef: MatDialogRef<EditCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { subCategoryId: any }
  ) {}

  ngOnInit(): void {
    this.updateSubCategoryForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.pattern(this.pattern),
        Validators.minLength(this.minLength),
        Validators.maxLength(this.maxLength),
      ]),
    });

    this.getSubcategoryName(this.subCategoryId);
  }
  get subCategoryId(): number {
    return this.data.subCategoryId;
  }
  handleInputChange() {
    this.showErrorMessage = false; // reset flag on input change
  }
  closeDialog() {
    this.updateSubCategoryForm.reset(); // Reset the form
    this.formSubmitted = false;
    this.dialog.closeAll();
    this.dialogRef.close();
  }
  onSubmit() {
    this.formSubmitted = true;
    if (this.updateSubCategoryForm.valid) {
      this.updateSubscription = this.categoryService
        .updateSubCategory(this.subCategoryId, this.updateSubCategoryForm.value)
        .subscribe({
          next: (result: any) => {
            this.dialog.closeAll();
            this.eventEmitter.onSaveEvent();
            this.toast.showSucessToast(
              'Sub Category updated Successfully',
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

  getSubcategoryName(id: any) {
    this.getDataSubscription = this.categoryService
      .getSubCategorydetail(id)
      .subscribe({
        next: (result: any) => {
          this.categories = result;
          this.subCategoryName = result.name;
          this.updateSubCategoryForm.patchValue({
            name: this.subCategoryName,
          });
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
  ngOnDestroy() {
    this.getDataSubscription.unsubscribe();
    this.dialog.closeAll();
  }
}
