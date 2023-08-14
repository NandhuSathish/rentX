import { TestBed } from '@angular/core/testing';

import { OtpGuard } from './otp.guard';
import { HttpClientModule } from '@angular/common/http';

describe('OtpGuard', () => {
  let guard: OtpGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule]
    });
    guard = TestBed.inject(OtpGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
