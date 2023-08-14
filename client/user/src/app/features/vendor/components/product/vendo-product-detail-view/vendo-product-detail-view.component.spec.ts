import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendoProductDetailViewComponent } from './vendo-product-detail-view.component';

describe('VendoProductDetailViewComponent', () => {
  let component: VendoProductDetailViewComponent;
  let fixture: ComponentFixture<VendoProductDetailViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendoProductDetailViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendoProductDetailViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
