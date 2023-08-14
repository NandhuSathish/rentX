import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorListComponent } from './vendor-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastService } from 'src/app/shared/services/toast.service';
import { MatDialogModule } from '@angular/material/dialog';

describe('VendorListComponent', () => {
  let component: VendorListComponent;
  let fixture: ComponentFixture<VendorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VendorListComponent],
      imports: [
        HttpClientModule,
        MatSnackBarModule,MatDialogModule
      ],
      providers: [ToastService]
    }).compileComponents();

    fixture = TestBed.createComponent(VendorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
