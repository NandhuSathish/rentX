import { TestBed } from '@angular/core/testing';

import { ResetpasswordGuard } from './resetpassword.guard';
import { HttpClientModule } from '@angular/common/http';

describe('ResetpasswordGuard', () => {
  let guard: ResetpasswordGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule]

    });
    guard = TestBed.inject(ResetpasswordGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
