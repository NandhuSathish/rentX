import { TestBed } from '@angular/core/testing';

import { ResetpasswordGuard } from './resetpassword.guard';

describe('ResetpasswordGuard', () => {
  let guard: ResetpasswordGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ResetpasswordGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
