import { TestBed } from '@angular/core/testing';

import { TokenInterceptorService } from './token-intercepter.service';
import { HttpClientModule } from '@angular/common/http';

describe('TokenInterceptorService', () => {
  let service: TokenInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule],
      providers: [TokenInterceptorService],
    });
    service = TestBed.inject(TokenInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
