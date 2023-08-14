import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from 'src/app/services/auth.service';
import { LoginComponent } from './login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let spy: jasmine.Spy;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
      providers: [AuthService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    spy = spyOn(window, 'alert').and.callThrough();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error message if login fails', () => {
    const email = component.loginForm.controls['email'];
    const password = component.loginForm.controls['password'];

    email.setValue('test@test.com');
    password.setValue('invalidPassword');

    const errorResponse = {
      error: {
        errorMessage: 'BAD_REQUEST',
      },
    };

    spy.calls.reset();

    spyOn(authService, 'login').and.returnValue(
      new Observable((observer) => {
        observer.error(errorResponse);
      })
    );

    component.onSubmit();

    expect(spy).toHaveBeenCalled();
    expect(spy.calls.first().args[0]).toEqual('Invalid Username or Password');
  });

  it('should navigate to home page on successful login', () => {
    const email = component.loginForm.controls['email'];
    const password = component.loginForm.controls['password'];

    email.setValue('test@test.com');
    password.setValue('validPassword@1');

    const successResponse = {
      accessToken: {
        value: 'token',
      },
      message: 'success',
    };

    spyOn(authService, 'login').and.returnValue(of(successResponse));

    const router = TestBed.inject(Router);
    const spyRouter = spyOn(router, 'navigateByUrl');

    component.onSubmit();

    expect(spyRouter).toHaveBeenCalledWith('/home');
  });
});
