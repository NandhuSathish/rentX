import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingVendorListComponent } from './pending-vendor-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ToastService } from 'src/app/shared/services/toast.service';
import { MatDialogModule } from '@angular/material/dialog';

describe('PendingVendorListComponent', () => {
  let component: PendingVendorListComponent;
  let fixture: ComponentFixture<PendingVendorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, MatSnackBarModule,MatDialogModule],
      declarations: [PendingVendorListComponent],
      providers: [ToastService],
    }).compileComponents();

    fixture = TestBed.createComponent(PendingVendorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
