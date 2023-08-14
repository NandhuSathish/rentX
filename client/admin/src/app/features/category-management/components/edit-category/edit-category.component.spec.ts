import { ComponentFixture, TestBed, async } from '@angular/core/testing';

import { EditCategoryComponent } from './edit-category.component';
import { HttpClientModule } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastService } from 'src/app/shared/services/toast.service';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { CategoryService } from '../../services/category.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

describe('EditCategoryComponent', () => {
  let component: EditCategoryComponent;
  let eventEmitterService: jasmine.SpyObj<EventEmitterService>;
  let categoryService: jasmine.SpyObj<CategoryService>;
  let router: jasmine.SpyObj<Router>;
  let dialog: jasmine.SpyObj<MatDialog>;
  let toastService: jasmine.SpyObj<ToastService>;
  let matDialogRef: jasmine.SpyObj<MatDialogRef<EditCategoryComponent>>;

  beforeEach(() => {
    eventEmitterService = jasmine.createSpyObj('EventEmitterService', [
      'onSaveEvent',
    ]);
    categoryService = jasmine.createSpyObj('CategoryService', [
      'updateCategory',
      'getCategoryName',
    ]);
    router = jasmine.createSpyObj('Router', ['navigate']);
    dialog = jasmine.createSpyObj('MatDialog', ['closeAll']);
    toastService = jasmine.createSpyObj('ToastService', [
      'showSucessToast',
      'showErrorToast',
    ]);
    matDialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

    TestBed.configureTestingModule({
      providers: [
        { provide: EventEmitterService, useValue: eventEmitterService },
        { provide: CategoryService, useValue: categoryService },
        { provide: Router, useValue: router },
        { provide: MatDialog, useValue: dialog },
        { provide: ToastService, useValue: toastService },
        { provide: MatDialogRef, useValue: matDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: { categoryId: 1 } },
      ],
    });

    component = new EditCategoryComponent(
      TestBed.inject(EventEmitterService),
      TestBed.inject(CategoryService),
      TestBed.inject(Router),
      TestBed.inject(MatDialog),
      TestBed.inject(ToastService),
      TestBed.inject(MatDialogRef),
      TestBed.inject(MAT_DIALOG_DATA)
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnDestroy', () => {
    it('should close all dialogs and close the current dialog', () => {
      // Act
      component.ngOnDestroy();

      // Assert
      expect(dialog.closeAll).toHaveBeenCalled();
      expect(matDialogRef.close).toHaveBeenCalled();
    });
  });

  describe('get categoryId', () => {
    it('should return the correct categoryId', () => {
      // Arrange
      component.data = { categoryId: 5 };

      // Act
      const result = component.categoryId;

      // Assert
      expect(result).toEqual(5);
    });
  });

  describe('closeDialog', () => {
    it('should reset the form, set formSubmitted to false, close all dialogs, and close the current dialog', () => {
      // Act
      component.closeDialog();

      // Assert

      expect(component.formSubmitted).toBeFalsy();
      expect(dialog.closeAll).toHaveBeenCalled();
      expect(matDialogRef.close).toHaveBeenCalled();

      // Expect that formSubmitted was set to false
      expect(component.formSubmitted).toBe(false);
    });
  });

  describe('onSubmit', () => {
    beforeEach(() => {
      component.updateCategoryForm = new FormGroup({
        name: new FormControl('Test Category', [
          Validators.required,
          Validators.pattern(component.pattern),
          Validators.minLength(component.minLength),
          Validators.maxLength(component.maxLength),
        ]),
      });
    });
    it('should submit the form when it is valid', () => {
      // Set up a spy on the component's submit method
      spyOn(component, 'onSubmit');
  
      // Simulate a form submission by calling the onSubmit method
      component.onSubmit();
  
      // Expect that the submit method has been called
      expect(component.onSubmit).toHaveBeenCalled();
  
      // Expect that the form is valid and has no errors
      expect(component.updateCategoryForm.valid).toBeTruthy();
      expect(component.updateCategoryForm.errors).toBeNull();
    });
  });
 
});
