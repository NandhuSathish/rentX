import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorToastComponent } from './error-toast.component';
import {
  MAT_SNACK_BAR_DATA,
  MatSnackBarModule,
  MatSnackBarRef,
} from '@angular/material/snack-bar';

describe('ErrorToastComponent', () => {
  let component: ErrorToastComponent;
  let fixture: ComponentFixture<ErrorToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ErrorToastComponent],
      imports: [MatSnackBarModule],
      providers: [
        { provide: MatSnackBarRef, useValue: {} },
        { provide: MAT_SNACK_BAR_DATA, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ErrorToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
