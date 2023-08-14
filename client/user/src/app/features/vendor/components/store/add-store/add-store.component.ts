import {
  Component,
  ElementRef,
  NgZone,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { StoreService } from '../../../services/store.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-add-store',
  templateUrl: './add-store.component.html',
  styleUrls: ['./add-store.component.css'],
})
export class AddStoreComponent implements OnInit {
  storeDetailsForm!: FormGroup;
  formSubmitted = false;
  buttonDisabler=false;
  phoneNumberRegexp = new RegExp(/^[0-9]{10,15}$/);
  pincodeRegex = new RegExp(/^[0-9]{6,8}$/);
  nameRegex = new RegExp(/^[a-zA-Z0-9_\s]{3,20}$/);
  roadRegex = new RegExp(
    /^(?!\s)[a-zA-Z0-9_\s!@#$%^&*()-+=~`{}[\]|:;"'<>,.?/]{3,20}(?<!\s)$/
  );
  constructor(
    private eventEmitter: EventEmitterService,
    private storeService: StoreService,
    private toastService: ToastService,
    private _ngZone: NgZone,
    private route: Router,
    private ngxService: NgxUiLoaderService,
  ) {}

  ngOnInit(): void {
    this.storeDetailsForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
      mobile: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(15),
        Validators.pattern(this.phoneNumberRegexp),
      ]),
      city: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
      state: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
      pincode: new FormControl('', [
        Validators.required,
        Validators.maxLength(8),
        Validators.pattern(this.pincodeRegex),
      ]),
      buildingName: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
      roadName: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
    });
  }
  // return an object with a random latitude and longitude
  randomLatLong() {
    const lat = Math.random() * 180 - 90;
    const long = Math.random() * 360 - 180;
    return { lattitude: lat, longitude: long };
  }

  // validateData(data: any) {
  //   return this.trimObjectStrings(this.storeDetailsForm.value);
  // }
  onStoreDetailsSubmit() {
    // console.log(this.storeDetailsForm.value);
    // console.log(this.validateData(this.storeDetailsForm.value));

    this.formSubmitted = true;
    if (this.storeDetailsForm.valid) {
      this.buttonDisabler=true;

      // this.ngxService.start();
      const storeDetailsData = {
        ...this.storeDetailsForm.value,
        ...this.randomLatLong(),
      };
      this.storeService.addStoreDetails(storeDetailsData).subscribe({
        next: (data: any) => {
          this.toastService.showSucessToast('Store added sucessfully', 'close');
          this._ngZone.run(() => {
            this.eventEmitter.onSaveEvent();
            this.route.navigate(['/vendor/stores']);
          });
        },
        error: () => {
          // this.ngxService.stop();
        },
      });
    }
  }
}
