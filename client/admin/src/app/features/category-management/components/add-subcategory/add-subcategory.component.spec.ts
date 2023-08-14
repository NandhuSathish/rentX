import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSubcategoryComponent } from './add-subcategory.component';
import { HttpClientModule } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastService } from 'src/app/shared/services/toast.service';

describe('AddSubcategoryComponent', () => {
  let component: AddSubcategoryComponent;
  let fixture: ComponentFixture<AddSubcategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSubcategoryComponent],
      imports: [FormsModule, ReactiveFormsModule, MatSnackBarModule ,HttpClientModule, MatDialogModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: {} },
        ToastService,
      ],
  
    }).compileComponents();

    fixture = TestBed.createComponent(AddSubcategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
