import {
  Component,
  EventEmitter,
  Inject,
  OnDestroy,
  OnInit,
} from '@angular/core';
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
  selector: 'app-add-subcategory',
  templateUrl: './add-subcategory.component.html',
  styleUrls: ['./add-subcategory.component.css'],
})
export class AddSubcategoryComponent implements OnInit, OnDestroy {
  formSubmitted = false;
  pattern = '^[a-zA-Z0-9]+(?:[ ][a-zA-Z0-9]+)*$';
  minLength = 3;
  maxLength = 20;
  errorData: any;
  showErrorMessage = false;
  addSubCategoryForm!: FormGroup;
  addSubcatgorySubscription!: Subscription;
  constructor(
    private eventEmitter: EventEmitterService,
    private dialogRef: MatDialogRef<AddSubcategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { categoryId: any },
    private categoryService: CategoryService,
    private route: Router,
    private dialog: MatDialog,
    public toast: ToastService
  ) {}
  ngOnDestroy(): void {
    this.dialog.closeAll();
  }

  ngOnInit(): void {
    this.addSubCategoryForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.pattern(this.pattern),
        Validators.minLength(this.minLength),
        Validators.maxLength(this.maxLength),
      ]),
    });
  }
  handleInputChange() {
    this.showErrorMessage = false; // reset flag on input change
  }
  closeDialog() {
    this.addSubCategoryForm.reset(); // Reset the form
    this.formSubmitted = false;
    this.dialog.closeAll();
    this.dialogRef.close();
  }
  get categoryId(): any {
    return this.data.categoryId;
  }
  onSubmit() {
    this.formSubmitted = true;
    if (this.addSubCategoryForm.valid) {
      // Assign the categoryId from this.data
      const name = this.addSubCategoryForm.get('name')?.value;
      const paramBody = {
        categoryId: this.categoryId,
        name: name,
      };

      this.addSubcatgorySubscription = this.categoryService
        .addSubCategory(paramBody)
        .subscribe({
          next: (result: any) => {
            this.dialog.closeAll();
            this.toast.showSucessToast(
              'Sub Category Added Successfully',
              'close'
            );
            this.eventEmitter.onSaveEvent();
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
}
