import { TestBed } from '@angular/core/testing';

import { UnAuthGuard } from './un-auth.guard';
import { HttpClientModule } from '@angular/common/http';

describe('UnAuthGuard', () => {
  let guard: UnAuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [UnAuthGuard]
    });
    guard = TestBed.inject(UnAuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
