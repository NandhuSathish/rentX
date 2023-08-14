import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorOtpVerifyComponent } from './vendor-otp-verify.component';

describe('VendorOtpVerifyComponent', () => {
  let component: VendorOtpVerifyComponent;
  let fixture: ComponentFixture<VendorOtpVerifyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VendorOtpVerifyComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VendorOtpVerifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
