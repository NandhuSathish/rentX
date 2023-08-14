import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SucessToastComponent } from './sucess-toast.component';

describe('SucessToastComponent', () => {
  let component: SucessToastComponent;
  let fixture: ComponentFixture<SucessToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SucessToastComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SucessToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
