import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorOrderComponent } from './vendor-order.component';

describe('VendorOrderComponent', () => {
  let component: VendorOrderComponent;
  let fixture: ComponentFixture<VendorOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorOrderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
