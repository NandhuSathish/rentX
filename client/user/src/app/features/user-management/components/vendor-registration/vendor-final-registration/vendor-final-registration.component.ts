import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { VendorService } from '../../../services/vendor.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-vendor-final-registration',
  templateUrl: './vendor-final-registration.component.html',
  styleUrls: ['./vendor-final-registration.component.css'],
})
export class VendorFinalRegistrationComponent implements OnInit {
  @ViewChild('mobileNumber', { static: true })
  mobileNumber: ElementRef<HTMLInputElement> | undefined;

  constructor(
    private vendorService: VendorService,
    private authService: AuthService
  ) {}

  vendorDetailForm!: FormGroup;
  vendorBankDetailForm!: FormGroup;

  userNameRegexp = new RegExp(/^[a-zA-Z0-9]{3,50}$/);
  onlyNumberRegexp = new RegExp(/^[0-9]+$/);
  ifscCodeRegexp = new RegExp(/^[A-Z0-9]{11}$/);
  accountHolderNameRegexp = new RegExp(/^[a-zA-Z]+$/);
  gstNoRegexp = new RegExp(/^[A-Z0-9]{15}$/);
  panNoRegexp = new RegExp(/^[A-Z0-9]{10}$/);

  isLinear = true;
  isEditable = false;
  formSubmitted=false;

  ngOnInit(): void {
    this.vendorDetailForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(50),
        Validators.minLength(3),
        Validators.pattern(this.userNameRegexp),
      ]),
      mobileNo: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(15),
        Validators.pattern(this.onlyNumberRegexp),
      ]),
    });

    this.vendorBankDetailForm = new FormGroup({
      accountHolderName: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.pattern(this.accountHolderNameRegexp),
      ]),
      accountNo: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(18),
        Validators.pattern(this.onlyNumberRegexp),
      ]),
      ifscCode: new FormControl('', [
        Validators.required,
        Validators.pattern(this.ifscCodeRegexp),
      ]),
      gstNo: new FormControl('', [
        Validators.required,
        Validators.pattern(this.gstNoRegexp),
      ]),
      panNo: new FormControl('', [
        Validators.required,
        Validators.pattern(this.panNoRegexp),
      ]),
    });
  }

  onSubmitDetails() {
    this.formSubmitted=true;
    this.vendorService
      .vendorRegistrationStageOne(
        this.vendorDetailForm.value.name,
        this.vendorDetailForm.value.mobileNo
      )
      .subscribe({
        next: (res: any) => {
          this.authService.storeEmailToken(res.emailToken);
        },
        error: () => {},
      });
  }
  onSubmitBankDetails() {
    this.vendorService
      .addVendorBankDetails(this.vendorBankDetailForm)
      .subscribe({
        next: (data: any) => {
          localStorage.clear();
        },
        error: () => {},
      });
  }
}
