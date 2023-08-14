import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSubcategoryComponent } from './edit-subcategory.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastService } from 'src/app/shared/services/toast.service';

describe('EditSubcategoryComponent', () => {
  let component: EditSubcategoryComponent;
  let fixture: ComponentFixture<EditSubcategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditSubcategoryComponent],
      imports: [
        HttpClientModule,
        FormsModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatDialogModule,
      ],
      providers: [ToastService, { provide: MatDialogRef, useValue: {} }, // Provide an empty object
      { provide: MAT_DIALOG_DATA, useValue: {} }, // Provide an empty object
   ],
    }).compileComponents();

    fixture = TestBed.createComponent(EditSubcategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
