import { TestBed } from '@angular/core/testing';

import { AlertService } from './alert.service';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';

describe('AlertService', () => {
  let service: AlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatDialogModule],
      providers: [MatDialog]
    });
    service = TestBed.inject(AlertService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
