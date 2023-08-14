
package com.innovature.rentx.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.innovature.rentx.entity.*;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.exception.ConflictException;
import com.innovature.rentx.form.*;
import com.innovature.rentx.repository.AdminRepository;
import com.innovature.rentx.repository.OtpRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.config.SecurityConfig;
import com.innovature.rentx.security.util.InvalidTokenException;
import com.innovature.rentx.security.util.SecurityUtil;
import com.innovature.rentx.security.util.TokenExpiredException;
import com.innovature.rentx.security.util.TokenGenerator;
import com.innovature.rentx.security.util.TokenGenerator.Status;
import com.innovature.rentx.security.util.TokenGenerator.Token;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.util.EmailContents;
import com.innovature.rentx.util.EmailUtil;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.NewEmailUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.innovature.rentx.security.AccessTokenUserDetailsService.PURPOSE_ACCESS_TOKEN;

@Service
public class UserServiceImpl implements UserService {

    private static final String PURPOSE_REFRESH_TOKEN = "REFRESH_TOKEN";

    private static final String PURPOSE_EMAIL_TOKEN = "EMAIL_TOKEN";

    private static final String PURPOSE_FORGET_TOKEN = "FORGET_TOKEN";

    private static final String PURPOSE_VENDOR_REG_STAGE_1 = "PURPOSE_VENDOR_REG_STAGE_1";

    private static final String PURPOSE_VENDOR_REG_STAGE_2 = "PURPOSE_VENDOR_REG_STAGE_2";

    public static final String SUCCESSFULLY_CREATED = "successfully created";

    public static final String OTP_VERIFIED = "otp Verified successfully";

    private static final String EMAIL_ERROR = "email.password.incorrect";

    private static final String DIGIT_FORMAT = "%10d";

    private static final String PURPOSE_CHANGE_PASSWORD = "CHANGE_PASSWORD";

    private static final String EMAIL_FOUND_ERROR = "email.found.error";

    private static final String INVALID_TOKEN_EMAIL = "Invalid.Token.Email";

    private static final String UNABLE_TO_PERFORM = "unable.to.perform.this.action";

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailContents emailContents;

    @Autowired
    private NewEmailUtil newEmailUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private SecurityConfig securityConfig;

    Random random1 = new Random();

    @Value("${google.id}")
    private String googleId;

    @Value("${purpose.token.expiry}")
    private Integer ex;

    @Value("${otp.expiry}")
    private Integer otpExp;

    @Override
    public LoginView login(LoginForm form) throws BadRequestException {

        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(
                () -> new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en")));
        if (user.getType() == User.Type.GOOGLE.value) {
            throw new BadRequestException((languageUtil.getTranslatedText(EMAIL_ERROR, null, "en")));
        }
        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_ERROR, null, "en"));
        }

        if (user.getStatus() == User.Status.UNVERIFIED.value && user.getRole() == User.Role.USER.value) {
            throw new BadRequestException((languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en")));

        }

        if (user.getStatus() == User.Status.INACTIVE.value ) {
            throw new BadRequestException((languageUtil.getTranslatedText("user.blocked", null, "en")));

        }
        if (user.getStatus() == User.Status.DELETE.value) {
            throw new BadRequestException((languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en")));

        }

        String id = String.format(DIGIT_FORMAT, user.getId());
        String roleId = String.format(DIGIT_FORMAT, user.getRole());

        Duration duration = Duration.ofMinutes(ex);
        if (user.getRole() == User.Role.USER.value && user.getStatus() == User.Status.ACTIVE.value
                || user.getRole() == User.Role.VENDOR.value && user.getStatus() == User.Status.ACTIVE.value
                || user.getRole() == User.Role.VENDOR.value &&  user.getStatus() == User.Status.BLOCKED.value) {

            Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, roleId,
                    securityConfig.getAccessTokenExpiry());
            Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + user.getPassword(), roleId,
                    securityConfig.getRefreshTokenExpiry());
            return new LoginView(user, accessToken, refreshToken);

        } else if (user.getRole() == User.Role.VENDOR.value && user.getStatus() == User.Status.VERIFIED.value
                || user.getStatus() == User.Status.STAGE1.value) {
            TokenGenerator.Token emailToken = tokenGenerator.create(PURPOSE_VENDOR_REG_STAGE_1, user.getEmail(), roleId,
                    duration);
            return new LoginView(user, emailToken.value);

        } else if (user.getStatus() == User.Status.STAGE2.value) {
            return new LoginView(user);
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_ERROR, null, "en"));
        }

    }

    @Override
    public RefreshTokenView refresh(RefreshTokenForm form) throws BadRequestException {
        Status status;
        try {
            status = tokenGenerator.verify(PURPOSE_REFRESH_TOKEN, form.getRefreshToken());
        } catch (InvalidTokenException e) {
            throw new BadRequestException("1091-Token is invalid", e);
        } catch (TokenExpiredException e) {
            throw new BadRequestException("1090-Authorization token expired", e);
        }
        int userid;
        try {
            userid = Integer.parseInt(status.data.substring(0, 10).trim());
        } catch (NumberFormatException e) {
            throw new BadRequestException("1091-Token is invalid", e);
        }

        String password = status.data.substring(10);

        User user = userRepository.findByIdAndPassword(userid, password).orElseThrow(BadRequestException::new);
        String roleId = String.format(DIGIT_FORMAT, user.getRole());

        String id = String.format(DIGIT_FORMAT, user.getId());
        Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, roleId,
                securityConfig.getAccessTokenExpiry());
        return new RefreshTokenView(
                new RefreshTokenView.TokenView(accessToken.value, accessToken.expiry),
                new RefreshTokenView.TokenView(form.getRefreshToken(), status.expiry));
    }

    @Override
    public UserView currentUser() {
        try {
            return new UserView(
                    userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                            () -> new BadRequestException(
                                    languageUtil.getTranslatedText("USER_NOT_FOUND", null, "en"))));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("Invalid.id.token1", null, "en"));
        }
    }

    private String generateOtp() {
        int otp = 100000 + random1.nextInt(900000);
        return String.valueOf(otp);
    }

    private Otp saveOtp(String email, String otpStr) {
        LocalTime exp = LocalTime.now();

        Otp emails = otpRepository.findByEmail(email);
        if (emails != null) {
            emails.setOtp(otpStr);
            emails.setExpiry(exp);
            emails.setStatus(Otp.STATUS.ACTIVE.value);

            otpRepository.save(emails);
            return emails;
        } else {
            Otp otpEntity = new Otp();
            otpEntity.setEmail(email);
            otpEntity.setOtp(otpStr);
            otpEntity.setExpiry(exp);
            otpEntity.setCreatedAt(new Date());
            otpEntity.setStatus(Otp.STATUS.ACTIVE.value);
            otpRepository.save(otpEntity);
            return otpEntity;
        }

    }

    private void sendVerificationEmail(String email, String otp) {
    String to = email;
    String content = emailContents.otpEmailContent(otp);

    String subject = "Email Verification";
    // emailUtil.sendEmail(to, subject, content);
    newEmailUtil.sendEmail(to, content,subject);

    }
    // private void sendVerificationEmail(String email, String otp) {
    //     String to = email;
    //     // String content = emailUtil.generateVerificationEmailContent(otp);
    //     String content = "Hello, " + "Please verify your account by this OTP :" + otp;
    //     String subject = "Email Verification";
    //     emailUtil.sendEmail(to, subject, content);
    // }

    private EmailTokenView generateToken(String email, String tokenType) {
        User emailUser = userRepository.findByEmailId(email);
        String roleId = String.format(DIGIT_FORMAT, emailUser.getRole());
        Duration duration = Duration.ofMinutes(ex);
        TokenGenerator.Token emailToken = tokenGenerator.create(tokenType, email, roleId, duration);
        return new EmailTokenView(SUCCESSFULLY_CREATED, emailToken.value);

    }

    @Override
    public EmailTokenView add(UserForm form, Byte userRole) throws BadRequestException {
        User emailUser = userRepository.findByEmailId(form.getEmail());
        if (emailUser == null) {

            String otpStr = generateOtp();
            saveOtp(form.getEmail(), otpStr);

            if (userRole == User.Role.USER.value) {
                userRepository.save(new User(
                        form.getEmail(),
                        passwordEncoder.encode(form.getPassword())));

                sendVerificationEmail(form.getEmail(), otpStr);

                EmailTokenView emailTok = generateToken(form.getEmail(), PURPOSE_EMAIL_TOKEN);
                return emailTok;

            } else {

                userRepository.save(new User(
                        form.getEmail(),
                        passwordEncoder.encode(form.getPassword())));
                User user = userRepository.findByEmailId(form.getEmail());
                user.setRole(User.Role.VENDOR.value);
                userRepository.save(user);

                sendVerificationEmail(form.getEmail(), otpStr);
                EmailTokenView emailTok = generateToken(form.getEmail(), PURPOSE_EMAIL_TOKEN);

                return emailTok;
            }

        } else {
            if ((emailUser.getStatus() == User.Status.UNVERIFIED.value && emailUser.getRole() == User.Role.USER.value)
                    || (emailUser.getStatus() == User.Status.UNVERIFIED.value
                            && emailUser.getRole() == User.Role.VENDOR.value)) {

                String otpStr = generateOtp();
                saveOtp(form.getEmail(), otpStr);

                sendVerificationEmail(form.getEmail(), otpStr);

                EmailTokenView emailTok = generateToken(form.getEmail(), PURPOSE_EMAIL_TOKEN);

                return emailTok;

            } else {

                throw new ConflictException(languageUtil.getTranslatedText("email.already.exist", null, "en"));
            }
        }

    }

    @Override
    public ResponseEntity<String> verify(@Valid OtpForm form) {

        Status status = verifyToken(PURPOSE_EMAIL_TOKEN, form.getEmailToken());

        String email = status.data;
        Otp otp = otpRepository.findByEmail(email);
        LocalTime myObj = LocalTime.now();
        var expDiff = otp.getExpiry().until(myObj, ChronoUnit.SECONDS);
        if ((form.getOtp().equals(otp.getOtp()))) {

            if (expDiff < 121) {

                User user = userRepository.findByEmailId(email);

                if (user.getRole() == User.Role.USER.value && user.getStatus() == User.Status.UNVERIFIED.value) {
                    user.setStatus(User.Status.ACTIVE.value);
                    userRepository.save(user);
                    otp.setStatus(Otp.STATUS.INACTIVE.value);
                    otpRepository.save(otp);
                    return new ResponseEntity<>(HttpStatus.OK);
                }

                else {
                    throw new BadRequestException(
                            languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
                }

            }

            else {
                throw new BadRequestException(languageUtil.getTranslatedText("otp.expired", null, "en"));
            }

        } else {
            throw new BadRequestException(languageUtil.getTranslatedText("Incorrect.otp", null, "en"));
        }
    }

    // Resend Otp
    @Override
    public EmailTokenView resend(@Valid ResendOtpForm form, Integer forgetValue) {
        Status status;
        EmailTokenView emailTok;

        if (forgetValue == 1) {
            status = verifyToken(PURPOSE_EMAIL_TOKEN, form.getEmailToken());
            Otp otp = otpRepository.findByEmail(status.data);

            if (otp.getStatus() == Otp.STATUS.ACTIVE.value) {
                emailTok = generateToken(status.data, PURPOSE_EMAIL_TOKEN);
            } else {
                throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));

            }

        }

        else if (forgetValue == 0) {
            status = verifyToken(PURPOSE_FORGET_TOKEN, form.getEmailToken());
            Otp otp = otpRepository.findByEmail(status.data);
            if (otp.getStatus() == Otp.STATUS.ACTIVE.value) {
                String roleIdString = status.roleId.trim();
                byte roleIdByte;

                try {
                    roleIdByte = Byte.parseByte(roleIdString);
                } catch (NumberFormatException e) {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
                }

                if (roleIdByte == User.Role.VENDOR.value || roleIdByte == User.Role.USER.value) {

                    emailTok = generateToken(status.data, PURPOSE_FORGET_TOKEN);
                } else {

                    emailTok = generateAdminToken(status.data, PURPOSE_FORGET_TOKEN);
                }
            } else {
                throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));

            }

        }

        else {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

        String email = status.data;
        Otp otp = otpRepository.findByEmail(email);

        String otpStr = generateOtp();
        saveOtp(otp.getEmail(), otpStr);
        sendVerificationEmail(email, otpStr);

        return emailTok;

    }

    private EmailTokenView generateAdminToken(String email, String tokenType) {

        Admin adminUser = adminRepository.findByEmailId(email);
        String roleId = String.format(DIGIT_FORMAT, adminUser.getRole());
        Duration duration = Duration.ofMinutes(ex);
        TokenGenerator.Token emailToken = tokenGenerator.create(tokenType, email, roleId, duration);
        return new EmailTokenView(SUCCESSFULLY_CREATED, emailToken.value);

    }

    @Override
    public VendorOtpView verifyVendorOtp(@Valid OtpForm form) {

        Status status = verifyToken(PURPOSE_EMAIL_TOKEN, form.getEmailToken());
        String email = status.data;
        Otp otp = otpRepository.findByEmail(email);
        if (otp.getStatus() == Otp.STATUS.ACTIVE.value) {

            LocalTime myObj = LocalTime.now();
            var expDiff = otp.getExpiry().until(myObj, ChronoUnit.SECONDS);
            if ((form.getOtp().equals(otp.getOtp()))) {

                if (expDiff < 121) {

                    User user = userRepository.findByEmailId(email);
                    if (user.getRole() == User.Role.VENDOR.value && user.getStatus() == User.Status.UNVERIFIED.value) {

                        user.setStatus(User.Status.VERIFIED.value);
                        otp.setStatus(Otp.STATUS.INACTIVE.value);
                        otpRepository.save(otp);
                        userRepository.save(user);
                    } else {
                        throw new BadRequestException(
                                languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
                    }

                }

                else {
                    throw new BadRequestException(languageUtil.getTranslatedText("otp.expired", null, "en"));
                }

            } else {
                throw new BadRequestException(languageUtil.getTranslatedText("Incorrect.otp", null, "en"));
            }

            try {
                User user = userRepository.findByEmailId(email);

                String roleId = String.format(DIGIT_FORMAT, user.getRole());

                Duration duration = Duration.ofMinutes(ex);

                TokenGenerator.Token emailToken = tokenGenerator.create(PURPOSE_VENDOR_REG_STAGE_1, email, roleId,
                        duration);

                return new VendorOtpView(OTP_VERIFIED, emailToken.value, user.getStatus(), user.getRole());

            } catch (NullPointerException e) {
                throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en"));
            }
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));

        }

    }

    private Status verifyToken(String tokenType, String token) {

        try {
            return tokenGenerator.verify(tokenType, token);
        } catch (InvalidTokenException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(INVALID_TOKEN_EMAIL, null, "en"), e);
        } catch (TokenExpiredException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("Token.expired", null, "en"), e);
        }
    }

    @Override
    public VendorRegStage1View vendorRegStage1(VendorRegStage1Form form) throws BadRequestException {

        Status status = verifyToken(PURPOSE_VENDOR_REG_STAGE_1, form.getEmailToken());

        String email = status.data;

        User user = userRepository.findByEmailId(email);

        if (user.getStatus() == User.Status.VERIFIED.value || user.getStatus() == User.Status.STAGE1.value) {

            user.setUsername(form.getUsername());
            user.setPhone(form.getPhone());
            user.setStatus(User.Status.STAGE1.value);
            userRepository.save(user);

            String roleId = String.format(DIGIT_FORMAT, user.getRole());

            // new email token generation
            Duration duration = Duration.ofMinutes(ex);
            TokenGenerator.Token vendorEmailToken = tokenGenerator.create(PURPOSE_VENDOR_REG_STAGE_2, email, roleId,
                    duration);

            return new VendorRegStage1View(user.getUsername(), user.getPhone(), vendorEmailToken.value);

        } else {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

    }

    @Override
    public LoginView googleAuth(GoogleSignInForm form, Byte userRole) throws GeneralSecurityException, IOException {
        var token = form.getToken();
        GoogleIdToken idToken = verifyToken(token);
        String email = idToken.getPayload().getEmail();

        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmailId(email);
            return handleExistingUser(user);
        } else {

            return handleNewUser(email, userRole);
        }
    }

    private GoogleIdToken verifyToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(token);

            if (idToken == null) {
                throw new BadRequestException(languageUtil.getTranslatedText("Invalid.id.token", null, "en"));
            } else {
                return idToken;
            }

        } catch (IllegalArgumentException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("Invalid.id.token", null, "en"));
        }

    }

    private LoginView handleExistingUser(User user) {

        if (user.getStatus() == User.Status.ACTIVE.value) {
            return handleActiveUser(user);
        } else if (user.getStatus() == User.Status.UNVERIFIED.value) {

            if (user.getRole() == User.Role.USER.value) {
                return handleInactiveUserWithRoleUser(user);
            } else if (user.getRole() == User.Role.VENDOR.value) {
                return handleInactiveVendorUser(user);
            }

        } else if (user.getStatus() == User.Status.VERIFIED.value) {

            return handlePendingVendor(user);

        } else if (user.getStatus() == User.Status.STAGE1.value) {

            return handleStage1Vendor(user);

        } else if (user.getStatus() == User.Status.STAGE2.value) {

            return handleStage2Vendor(user);

        } else if (user.getStatus() == User.Status.INACTIVE.value) {

            throw new BadRequestException(languageUtil.getTranslatedText("user.blocked", null, "en"));
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

        return null;

    }

    private LoginView handleActiveUser(User user) {

        String roleId = String.format(DIGIT_FORMAT, user.getRole());
        String id = String.format(DIGIT_FORMAT, user.getId());

        TokenGenerator.Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, roleId,
                securityConfig.getAccessTokenExpiry());
        TokenGenerator.Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN,
                id + user.getPassword(), roleId, securityConfig.getRefreshTokenExpiry());
        return new LoginView(user, accessToken, refreshToken);
    }

    private LoginView handleInactiveUserWithRoleUser(User user) {
        user.setStatus(User.Status.ACTIVE.value);
        userRepository.save(user);
        return handleActiveUser(user);

    }

    private LoginView generatePurposeTokenForVendor(String email, String tokenType) {
        User emailUser = userRepository.findByEmailId(email);
        String roleId = String.format(DIGIT_FORMAT, emailUser.getRole());
        Duration duration = Duration.ofMinutes(ex);
        TokenGenerator.Token emailToken = tokenGenerator.create(tokenType, email, roleId, duration);
        return new LoginView(emailUser, emailToken.value);

    }

    private LoginView handleInactiveVendorUser(User user) {

        user.setStatus(User.Status.VERIFIED.value);
        userRepository.save(user);
        return generatePurposeTokenForVendor(user.getEmail(), PURPOSE_VENDOR_REG_STAGE_1);

    }

    private LoginView handlePendingVendor(User user) {

        return generatePurposeTokenForVendor(user.getEmail(), PURPOSE_VENDOR_REG_STAGE_1);
    }

    private LoginView handleStage1Vendor(User user) {

        return generatePurposeTokenForVendor(user.getEmail(), PURPOSE_VENDOR_REG_STAGE_2);

    }

    private LoginView handleStage2Vendor(User user) {

        String message = "Waiting for admin approval";
        String msg = "please wait";

        return new LoginView(user, message, msg);
    }

    private LoginView handleNewUser(String email, Byte userRole) {

        if (userRole == User.Role.USER.value) {
            new UserView(
                    userRepository.save(new User(
                            email)));

            User user = userRepository.findByEmailId(email);
            return handleActiveUser(user);
        } else if (userRole == User.Role.VENDOR.value) {
            User user = new User();
            user.setEmail(email);
            user.setRole(User.Role.VENDOR.value);
            user.setType(User.Type.GOOGLE.value);
            user.setStatus(User.Status.VERIFIED.value);
            userRepository.save(user);

            return handlePendingVendor(user);
        }

        return null;
    }

    public static final String SUCCESS_STRING = "successfully created";

    @Override
    public EmailTokenView forgetPasswordEmail(EmailForm form) throws BadRequestException {

        User forgetUser = userRepository.findByEmailAndStatus(form.getEmail(), User.Status.ACTIVE.value);

        if (forgetUser == null) {
            throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en"));
        } else if (forgetUser.getType() == User.Type.GOOGLE.value) {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));

        }

        else {
            String otpStr = generateOtp();

            saveOtp(form.getEmail(), otpStr);

            sendVerificationEmail(form.getEmail(), otpStr);

            return generateToken(form.getEmail(), PURPOSE_FORGET_TOKEN);

        }

    }

    @Override
    public EmailTokenView forgetVerify(@Valid OtpForm form) {

        Status status = verifyToken(PURPOSE_FORGET_TOKEN, form.getEmailToken());
        String email = status.data;
        Otp otp = otpRepository.findByEmail(email);
        if (otp.getStatus() == Otp.STATUS.ACTIVE.value) {
            LocalTime myObj = LocalTime.now();
            var expDiff = otp.getExpiry().until(myObj, ChronoUnit.SECONDS);

            if ((form.getOtp().equals(otp.getOtp()))) {
                if (expDiff < 121) {

                    User user = userRepository.findByEmailId(email);
                    user.setStatus(User.Status.ACTIVE.value);
                    userRepository.save(user);
                    otp.setStatus(Otp.STATUS.INACTIVE.value);
                    otpRepository.save(otp);
                    EmailTokenView emailTok = generateToken(email, PURPOSE_CHANGE_PASSWORD);
                    return emailTok;

                }
                throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en"));

            } else {
                throw new BadRequestException("1042-Incorrect otp");
            }
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));

        }

    }

    @Override
    public ResponseEntity<String> changePassword(@Valid ChangePasswordForm form) {

        Status status = verifyToken(PURPOSE_CHANGE_PASSWORD, form.getEmailToken());

        String email = status.data;
        User user = userRepository.findByEmailId(email);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.save(user);

        String message = "password changed successfully";
        String json = String.format("{\"message\":\"%s\"}", message);
        return ResponseEntity.ok(json);

    }

}