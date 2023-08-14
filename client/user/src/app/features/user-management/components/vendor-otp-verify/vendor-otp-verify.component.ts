import { Component, NgZone, OnInit, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { VendorService } from '../../services/vendor.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-vendor-otp-verify',
  templateUrl: './vendor-otp-verify.component.html',
  styleUrls: ['./vendor-otp-verify.component.css'],
})
export class VendorOtpVerifyComponent implements OnInit {
  formSubmitted = false;
  otpForm!: FormGroup;
  otpNumberRegexp = new RegExp(/^[0-9]{1,6}$/);
  errorData: any;
  remainingTime = 120;
  intervalId: any;
  timerEnded = false;
  isResendClicked=false;
  constructor(
    private vendorService: VendorService,
    private router: Router,
    private _ngZone: NgZone,
    public toast: ToastService,
    private authService: AuthService,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.otpForm = new FormGroup({
      otp: new FormControl('', [
        Validators.required,
        Validators.pattern(this.otpNumberRegexp),
      ]),
    });
    // resend otp timer starts
    if(localStorage.getItem('timerEnded')=='true'){
      this.timerEnded=true;
    }else{

      this.startTimer();
    }
  }

  startTimer() {
    const storedTime = localStorage.getItem('remainingTime');

    if (storedTime) {
      this.remainingTime = parseInt(storedTime, 10);
    }
    this.intervalId = setInterval(() => {
      this.tick();
    }, 1000);
  }

  tick() {
    this.remainingTime--;
    localStorage.setItem('remainingTime', this.remainingTime.toString());

    if (this.remainingTime === 0) {
      clearInterval(this.intervalId);
      localStorage.removeItem('remainingTime');
      this.timerEnded = true;
      localStorage.setItem('timerEnded',this.timerEnded.toString())
      this.isResendClicked=false;
    }
  }

  ngOnDestroy() {
    this.stopTimer();
    localStorage.removeItem('remainingTime');
  }

  stopTimer() {
    clearInterval(this.intervalId);
  }
  onResendOtp() {
    this.isResendClicked=true;
    this.authService.resendOtp().subscribe({
      next:()=>{
        this.timerEnded = false;
        this.remainingTime=120;
        this.startTimer()
        
      },error:(error)=>{
        this.toast.showErrorToast(error.error.errorMessage,"close")
        this.router.navigateByUrl('/register')
      }
    });
  }

  onSubmit() {
    this.formSubmitted = true;
    if (this.otpForm.valid) {
      this.vendorService.verifyVendorOtp(this.otpForm.value.otp).subscribe({
        next: (res: any) => {
          this.authService.stagedVendorToken(res.emailToken,res.role,res.status);
          this.toast.showSucessToast('OTP Verified Successfully!', 'close');
          this._ngZone.run(() => {
            this.router.navigate(['/vendorRegisterFinal']);
          });
        },
        error: (data: any) => {
          this.errorData = data.error.errorMessage;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
        },
      });
    }
  }
}
