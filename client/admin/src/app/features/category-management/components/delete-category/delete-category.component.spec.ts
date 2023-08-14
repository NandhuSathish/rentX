import {
  ComponentFixture,
  TestBed,
  async,
  fakeAsync,
  tick,
} from '@angular/core/testing';

import { DeleteCategoryComponent } from './delete-category.component';
import { HttpClientModule } from '@angular/common/http';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from 'src/app/shared/components/input/input.component';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryService } from '../../services/category.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { of } from 'rxjs';
describe('DeleteCategoryComponent', () => {
  let component: DeleteCategoryComponent;
  let fixture: ComponentFixture<DeleteCategoryComponent>;
  let matDialogRefSpy: jasmine.SpyObj<MatDialogRef<DeleteCategoryComponent>>;

  beforeEach(async () => {
    const matDialogRefSpyObj = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      imports:[HttpClientModule,MatSnackBarModule],
      declarations: [DeleteCategoryComponent],
      providers: [
        { provide: MatDialogRef, useValue: matDialogRefSpyObj },
        { provide: MAT_DIALOG_DATA, useValue: { categoryId: '1' } },
      ],
    }).compileComponents();

    matDialogRefSpy = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<DeleteCategoryComponent>>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call dialogRef.close() when ngOnDestroy is called', () => {
    component.ngOnDestroy();
    expect(matDialogRefSpy.close).toHaveBeenCalled(); // Make sure that close() function is called on the dialog ref object
  });
});