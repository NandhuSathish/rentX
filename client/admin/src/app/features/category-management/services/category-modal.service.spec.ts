import { TestBed } from '@angular/core/testing';

import { CategoryModalService } from './category-modal.service';
import { MatDialogModule } from '@angular/material/dialog';

describe('CategoryModalService', () => {
  let service: CategoryModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      providers: [CategoryModalService]
    });
    service = TestBed.inject(CategoryModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
