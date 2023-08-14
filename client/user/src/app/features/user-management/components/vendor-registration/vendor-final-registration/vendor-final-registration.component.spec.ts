import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VendorFinalRegistrationComponent } from './vendor-final-registration.component';
import { VendorService } from '../../../services/vendor.service';
import { AuthService } from 'src/app/services/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('VendorFinalRegistrationComponent', () => {
  let component: VendorFinalRegistrationComponent;
  let fixture: ComponentFixture<VendorFinalRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VendorFinalRegistrationComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      providers: [VendorService, AuthService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VendorFinalRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have a vendorDetailForm with correct validators', () => {
    expect(component.vendorDetailForm).toBeDefined();
    expect(component.vendorDetailForm.controls['name'].validator).toBeTruthy();
    expect(
      component.vendorDetailForm.controls['mobileNo'].validator
    ).toBeTruthy();
  });

  it('should have a vendorBankDetailForm with correct validators', () => {
    expect(component.vendorBankDetailForm).toBeDefined();
    expect(
      component.vendorBankDetailForm.controls['accountHolderName'].validator
    ).toBeTruthy();
    expect(
      component.vendorBankDetailForm.controls['accountNo'].validator
    ).toBeTruthy();
    expect(
      component.vendorBankDetailForm.controls['ifscCode'].validator
    ).toBeTruthy();
    expect(
      component.vendorBankDetailForm.controls['gstNo'].validator
    ).toBeTruthy();
    expect(
      component.vendorBankDetailForm.controls['panNo'].validator
    ).toBeTruthy();
  });
});
