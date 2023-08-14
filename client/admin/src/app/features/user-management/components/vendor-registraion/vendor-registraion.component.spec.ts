import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorRegistraionComponent } from './vendor-registraion.component';

describe('VendorRegistraionComponent', () => {
  let component: VendorRegistraionComponent;
  let fixture: ComponentFixture<VendorRegistraionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorRegistraionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorRegistraionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
