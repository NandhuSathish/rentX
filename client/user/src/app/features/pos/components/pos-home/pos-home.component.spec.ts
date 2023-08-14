import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PosHomeComponent } from './pos-home.component';

describe('PosHomeComponent', () => {
  let component: PosHomeComponent;
  let fixture: ComponentFixture<PosHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PosHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PosHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
