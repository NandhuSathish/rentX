import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WarningToastComponent } from './warning-toast.component';

describe('WarningToastComponent', () => {
  let component: WarningToastComponent;
  let fixture: ComponentFixture<WarningToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WarningToastComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WarningToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
