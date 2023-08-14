import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavVendorComponent } from './nav-vendor.component';

describe('NavVendorComponent', () => {
  let component: NavVendorComponent;
  let fixture: ComponentFixture<NavVendorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavVendorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavVendorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
