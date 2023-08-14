import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubCategoryListComponent } from './sub-category-list.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryService } from '../../services/category.service';

describe('SubCategoryListComponent', () => {
  let component: SubCategoryListComponent;
  let fixture: ComponentFixture<SubCategoryListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubCategoryListComponent ],
      imports: [MatDialogModule,RouterTestingModule,HttpClientModule,MatSnackBarModule],
      providers: [{ provide: MatDialog, useValue: {}, CategoryService,ToastService,EventEmitterService
    }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubCategoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
