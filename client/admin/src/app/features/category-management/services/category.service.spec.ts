import { TestBed } from '@angular/core/testing';

import { CategoryService } from './category.service';
import { HttpClientModule } from '@angular/common/http';

describe('CategoryService', () => {
  let service: CategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [CategoryService]
    });
    service = TestBed.inject(CategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
