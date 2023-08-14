import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SucessToastComponent } from './sucess-toast.component';
import { MatSnackBarModule, MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

describe('SucessToastComponent', () => {
  let component: SucessToastComponent;
  let fixture: ComponentFixture<SucessToastComponent>;
  let snackBarRefSpy: jasmine.SpyObj<MatSnackBarRef<SucessToastComponent>>;

  beforeEach(async () => {
    snackBarRefSpy = jasmine.createSpyObj('MatSnackBarRef', ['dismiss']);

    await TestBed.configureTestingModule({
      declarations: [SucessToastComponent],
      imports: [MatSnackBarModule],
      providers: [
        { provide: MatSnackBarRef, useValue: snackBarRefSpy },
        { provide: MAT_SNACK_BAR_DATA, useValue: {} }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SucessToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
