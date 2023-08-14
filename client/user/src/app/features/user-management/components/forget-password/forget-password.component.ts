
import { HttpClient } from '@angular/common/http';
import { Component, NgZone, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css'],
})
export class ForgetPasswordComponent implements OnInit {

  form=1;
  remainingTime = 120;
  intervalId: any;
  timerEnded = false;
  isResendClicked=false;
  isOTPSend = false;

  emailregexp = new RegExp(/^(?!.*\.{2})[A-Za-z0-9._%+-]{1,20}(?<!\.)@[A-Za-z0-9.-]{1,25}\.[A-Za-z]{2,5}$/);
  otpNumberRegexp = new RegExp(/^[0-9]{1,6}$/);
  emailForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.pattern(this.emailregexp), Validators.required]),
  });

  otpForm: FormGroup = new FormGroup({
    otp: new FormControl('', [
      Validators.required,
      Validators.pattern(this.otpNumberRegexp),
    ]),
  });
  errorData: any;

  constructor(
    public httpClient:HttpClient,
    public route: Router,
    private authService: AuthService,
    public toast : ToastService,
    private ngxService: NgxUiLoaderService,
    private _ngZone: NgZone
  ) {
    /* TODO document why this constructor is empty */
  }

  ngOnInit(): void {
    
    if(localStorage.getItem('form'))
    {this.form=2;
      if(localStorage.getItem('remainingTime')==null){
        this.timerEnded=true;
      }
      else(this.startTimer())
  }
    else{this.form=1}
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
    this.authService.forgotResendOtp().subscribe({
      next:(res)=>{
        this.timerEnded = false;
        this.remainingTime=120;
        localStorage.setItem('emailToken', res.emailToken);
        this.startTimer();
        
      },error:(error)=>{
        this.toast.showErrorToast(error.error.errorMessage,"close")
        this.route.navigateByUrl('/login')
      }
    });
  }
  onSubmit() {
    if (this.emailForm.valid) {
      this.ngxService.start();
      this.isOTPSend = true;
  
      this.authService.ForgotPassword(this.emailForm.value).subscribe({
        next: (res) => {
          // Delay setting isOTPSend to true by 3 seconds
          
           // Remove this line from here
  
          localStorage.setItem('form', '1');
          this.ngxService.stop();
          this.toast.showSucessToast('OTP Sent', 'close');
          this.form = 2;
          this.authService.storeEmailToken(res.emailToken);
          this.startTimer();
        },
        error: (error) => {
          setTimeout(() => {
          this.isOTPSend = false;
          this.ngxService.stop();
          this.errorData = error.error.errorMessage;
          this.errorData = this.errorData.charAt(0).toUpperCase() + this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
          this.isOTPSend = false;
        }, 800);
        }
      });
    } else {
      this.emailForm.markAllAsTouched();
    }
  }
  

  onOtpVerify() {
    if (this.otpForm.valid) {
      this.authService
        .OtpVerify(this.otpForm.value.otp).subscribe({
          next:(res)=>{
            this.toast.showSucessToast('OTP Verified','close');
            this._ngZone.run(() => {
              this.route.navigateByUrl('/resetPassword');
            });
            localStorage.setItem('passwordToken', res.emailToken);
           },
          error: (error) => {
            this.errorData = error.error.errorMessage;
            this.errorData = this.errorData.charAt(0).toUpperCase() + this.errorData.slice(1).toLowerCase();
            this.toast.showErrorToast(this.errorData, 'close');
          }
        })
    } else {
      this.otpForm.markAllAsTouched();
    }
  }
}
