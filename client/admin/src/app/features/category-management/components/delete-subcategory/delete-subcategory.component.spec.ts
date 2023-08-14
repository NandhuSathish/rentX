import { ComponentFixture, TestBed, async } from '@angular/core/testing';

import { DeleteSubcategoryComponent } from './delete-subcategory.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryService } from '../../services/category.service';
import { of } from 'rxjs';
import { DeleteCategoryComponent } from '../delete-category/delete-category.component';

describe('DeleteSubcategoryComponent', () => {
  let component: DeleteSubcategoryComponent;
  let httpClient: jasmine.SpyObj<HttpClient>;
  let eventEmitterService: jasmine.SpyObj<EventEmitterService>;
  let categoryService: jasmine.SpyObj<CategoryService>;
  let matDialogRef: jasmine.SpyObj<MatDialogRef<DeleteCategoryComponent>>;
  let toastService: jasmine.SpyObj<ToastService>;

  beforeEach(() => {
    eventEmitterService = jasmine.createSpyObj('EventEmitterService', [
      'onDeleteEvent',
    ]);
    categoryService = jasmine.createSpyObj('CategoryService', [
      'DeleteSubCategory',
    ]);
    matDialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);
    toastService = jasmine.createSpyObj('ToastService', [
      'showSucessToast',
      'showErrorToast',
    ]);

    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        { provide: EventEmitterService, useValue: eventEmitterService },
        { provide: CategoryService, useValue: categoryService },
        { provide: MatDialogRef, useValue: matDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: { subCategoryId: 1 } },
        { provide: ToastService, useValue: toastService },
      ],
    });

    component = new DeleteSubcategoryComponent(
      TestBed.inject(HttpClient),
      TestBed.inject(EventEmitterService),
      TestBed.inject(CategoryService),
      TestBed.inject(MatDialogRef),
      TestBed.inject(MAT_DIALOG_DATA),
      TestBed.inject(ToastService)
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('get subCategoryId', () => {
    it('should return the correct subCategoryId', () => {
      // Arrange
      component.data = { subCategoryId: 5 };

      // Act
      const result = component.subCategoryId;

      // Assert
      expect(result).toEqual(5);
    });
  });

  describe('onCancel', () => {
    it('should close the dialog', () => {
      // Act
      component.onCancel();

      // Assert
      expect(matDialogRef.close).toHaveBeenCalled();
    });
  });

});
