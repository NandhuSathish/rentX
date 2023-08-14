import {
  ComponentFixture,
  TestBed,
  tick,
  fakeAsync,
} from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ToastService } from 'src/app/shared/services/toast.service';
import { VendorInitialRegistrationComponent } from './vendor-initial-registration.component';

describe('VendorInitialRegistrationComponent', () => {
  let component: VendorInitialRegistrationComponent;
  let fixture: ComponentFixture<VendorInitialRegistrationComponent>;
  let mockNgxUiLoaderService: jasmine.SpyObj<NgxUiLoaderService>;
  let mockToastService: jasmine.SpyObj<ToastService>;

  beforeEach(async () => {
    mockNgxUiLoaderService = jasmine.createSpyObj('NgxUiLoaderService', [
      'start',
      'stop',
    ]);
    mockToastService = jasmine.createSpyObj('ToastService', ['showErrorToast']);

    await TestBed.configureTestingModule({
      declarations: [VendorInitialRegistrationComponent],
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      providers: [
        { provide: NgxUiLoaderService, useValue: mockNgxUiLoaderService },
        { provide: ToastService, useValue: mockToastService },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VendorInitialRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create the vendorRegistrationForm', () => {
    component.ngOnInit();
    expect(component.vendorRegistrationForm).toBeTruthy();
  });
});
