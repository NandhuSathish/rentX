import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { CredentialResponse } from 'google-one-tap';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from '../../services/user.service';
import { passwordMatch } from '../../validators/passwordMatchValidators';
import { UserRegistraionComponent } from './user-registraion.component';
import { HttpClientModule } from '@angular/common/http';

describe('UserRegistraionComponent', () => {
  let component: UserRegistraionComponent;
  let fixture: ComponentFixture<UserRegistraionComponent>;
  let authService: AuthService;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, RouterTestingModule, HttpClientModule],
      declarations: [UserRegistraionComponent],
      providers: [AuthService, UserService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRegistraionComponent);
    component = fixture.componentInstance;
    // authService = TestBed.inject(AuthService);
    // userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a user registration form with required controls', () => {
    expect(component.userRegistrationForm.contains('email')).toBeTrue();
    expect(component.userRegistrationForm.contains('password')).toBeTrue();
    expect(
      component.userRegistrationForm.contains('confirmPassword')
    ).toBeTrue();
  });

  it('should require email, password, and confirmPassword fields in the form', () => {
    const emailControl = component.userRegistrationForm.get('email');
    const passwordControl = component.userRegistrationForm.get('password');
    const confirmPasswordControl =
      component.userRegistrationForm.get('confirmPassword');

    emailControl?.setValue('');
    passwordControl?.setValue('');
    confirmPasswordControl?.setValue('');

    expect(emailControl?.valid).toBeFalse();
    expect(passwordControl?.valid).toBeFalse();
    expect(confirmPasswordControl?.valid).toBeFalse();

    emailControl?.setValue('test@example.com');
    passwordControl?.setValue('Password@123');
    confirmPasswordControl?.setValue('Password@123');

    expect(emailControl?.valid).toBeTrue();
    expect(passwordControl?.valid).toBeTrue();
    expect(confirmPasswordControl?.valid).toBeTrue();
  });

  it('should require a valid email format', () => {
    const emailControl = component.userRegistrationForm.get('email');

    emailControl?.setValue('test@example');
    expect(emailControl?.valid).toBeFalse();

    emailControl?.setValue('test@example.com');
    expect(emailControl?.valid).toBeTrue();
  });

  it('should require a password with a minimum length of 8 characters', () => {
    const passwordControl = component.userRegistrationForm.get('password');

    passwordControl?.setValue('1234567');
    expect(passwordControl?.valid).toBeFalse();

    passwordControl?.setValue('12345Aa@');
    expect(passwordControl?.valid).toBeTrue();
  });

  it('should require a password with a maximum length of 16 characters', () => {
    const passwordControl = component.userRegistrationForm.get('password');

    passwordControl?.setValue('Aa@45678901234567');
    expect(passwordControl?.valid).toBeFalse();

    passwordControl?.setValue('Aa@4567890123456');
    expect(passwordControl?.valid).toBeTrue();
  });
});
