package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.Admin;
import com.innovature.rentx.entity.Otp;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.AdminForm;
import com.innovature.rentx.form.ChangePasswordForm;
import com.innovature.rentx.form.EmailForm;
import com.innovature.rentx.form.LoginForm;
import com.innovature.rentx.form.OtpForm;
import com.innovature.rentx.form.RefreshTokenForm;
import com.innovature.rentx.repository.AdminRepository;
import com.innovature.rentx.repository.OtpRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.config.SecurityConfig;
import com.innovature.rentx.security.util.InvalidTokenException;
import com.innovature.rentx.security.util.TokenExpiredException;
import com.innovature.rentx.security.util.TokenGenerator;
import com.innovature.rentx.service.AdminService;
import com.innovature.rentx.util.EmailUtil;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AdminLoginView;
import com.innovature.rentx.view.AdminVendorView;
import com.innovature.rentx.view.EmailTokenView;
import com.innovature.rentx.view.RefreshTokenView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.innovature.rentx.security.util.TokenGenerator.Status;
import com.innovature.rentx.security.util.TokenGenerator.Token;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;


import static com.innovature.rentx.security.AccessTokenUserDetailsService.PURPOSE_ACCESS_TOKEN;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String PURPOSE_REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String PURPOSE_EMAIL_TOKEN = "EMAIL_TOKEN";
    private static final String PURPOSE_FORGET_TOKEN = "FORGET_TOKEN";

    private static final String PURPOSE_CHANGE_PASSWORD = "CHANGE_PASSWORD";
    private static final String DIGIT_FORMAT = "%10d";
    public static final String OTP_VERIFIED = "otp Verified successfully";
    public static final String INVALID_TOKEN = "Invalid.Token.Email";
    public static final String INVALID_TOKEN1 = "Invalid.Token.Email";

    private static final String EMAIL_FOUND_ERROR = "email.found.error";

    private static final String UNABLE_PERFORM = "unable.to.perform.this.action";

    private final byte[] vendorStatus = { User.Status.ACTIVE.value, User.Status.INACTIVE.value,
            User.Status.STAGE2.value ,User.Status.BLOCKED.value};
    private byte[] vendorStatusAccept;

    private final byte[] userListStatus={User.Status.ACTIVE.value,User.Status.BLOCKED.value};
    private final EntityManager entityManager;

    @Autowired
    private LanguageUtil languageUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Value("${purpose.token.expiry}")
    private Integer ex;

    @Autowired
    private SecurityConfig securityConfig;
    Random random1 = new Random();

    public AdminServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ResponseEntity<String> add(AdminForm form) throws BadRequestException {
        Optional<Admin> admin = adminRepository.findByEmailAndRole(form.getEmail(), Admin.Role.ADMIN.value);
        if (admin.isEmpty()) {
            adminRepository.save(new Admin(form.getEmail(), passwordEncoder.encode(form.getPassword())));
            String message = "Admin Created successfully";
            return new ResponseEntity<>("{\"message\":\"" + message + "\"}", HttpStatus.OK);
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText("email.already.exist", null, "en"));
        }
    }

    @Override
    public AdminLoginView login(LoginForm form) throws BadRequestException {

        Admin admin = adminRepository.findByEmailAndRole(form.getEmail(), Admin.Role.ADMIN.value).orElseThrow(
                () -> new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en")));
        if (!passwordEncoder.matches(form.getPassword(), admin.getPassword())) {
            throw new BadRequestException(languageUtil.getTranslatedText("email.password.incorrect", null, "en"));
        }

        String id = String.format(DIGIT_FORMAT, admin.getId());
        String roleId = String.format(DIGIT_FORMAT, admin.getRole());

        TokenGenerator.Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, roleId,
                securityConfig.getAccessTokenExpiry());
        TokenGenerator.Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + admin.getPassword(),
                roleId,
                securityConfig.getRefreshTokenExpiry());
        return new AdminLoginView(admin, accessToken, refreshToken);
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

        Admin user = adminRepository.findByIdAndPassword(userid, password).orElseThrow(BadRequestException::new);
        String roleId = String.format(DIGIT_FORMAT, user.getRole());

        String id = String.format(DIGIT_FORMAT, user.getId());
        Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, roleId,
                securityConfig.getAccessTokenExpiry());
        return new RefreshTokenView(
                new RefreshTokenView.TokenView(accessToken.value, accessToken.expiry),
                new RefreshTokenView.TokenView(form.getRefreshToken(), status.expiry));
    }

    @Override
    public EmailTokenView forgetPasswordEmail(EmailForm form) throws BadRequestException {

        Admin forgetUser = adminRepository.findByEmailId(form.getEmail());
        if (forgetUser == null) {
            throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null, "en"));
        } else {
            int otp = 100000 + random1.nextInt(900000);
            LocalTime exp = LocalTime.now();

            Date dt = new Date();

            Otp otpEntity = new Otp();
            String otpStr = String.valueOf(otp);
            otpEntity.setOtp(otpStr);
            otpEntity.setEmail(form.getEmail());
            otpEntity.setExpiry(exp);
            otpEntity.setCreatedAt(dt);
            otpEntity.setStatus(Otp.STATUS.ACTIVE.value);
            Otp email = otpRepository.findByEmail(form.getEmail());
            if (email != null) {
                email.setOtp(otpStr);
                email.setExpiry(exp);
                email.setStatus(Otp.STATUS.ACTIVE.value);
                otpRepository.save(email);
            } else
                otpRepository.save(otpEntity);

            String to = form.getEmail();
            String content = "Hello, " + "Please verify your account by this OTP :" + otp;
            String subject = "Email Verification";
            emailUtil.sendEmail(to, subject, content);
            String roleId = String.format(DIGIT_FORMAT, forgetUser.getRole());

            Duration duration = Duration.ofMinutes(ex);

            TokenGenerator.Token emailToken = tokenGenerator.create(PURPOSE_FORGET_TOKEN, to, roleId, duration);
            String msg = "successfully created";
            return new EmailTokenView(msg, emailToken.value);

        }

    }

    @Override
    public EmailTokenView forgetVerify(@Valid OtpForm form) {
        Status status;
        try {

            status = tokenGenerator.verify(PURPOSE_FORGET_TOKEN, form.getEmailToken());

        } catch (InvalidTokenException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(INVALID_TOKEN, null, "en"), e);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(INVALID_TOKEN1, null, "en"), e);

        }

        String email = status.data;
        Otp otp = otpRepository.findByEmail(email);

        LocalTime myObj = LocalTime.now();

        var exp = otp.getExpiry().until(myObj, ChronoUnit.SECONDS);

        if ((form.getOtp().equals(otp.getOtp()))) {

            if (exp < 121) {

                Admin user = adminRepository.findByEmailId(email);
                if (user == null) {
                    throw new BadRequestException(languageUtil.getTranslatedText(EMAIL_FOUND_ERROR, null,
                            "en"));
                }
                Otp newotp = otpRepository.findByEmail(email);
                user.setStatus(User.Status.ACTIVE.value);
                otpRepository.save(newotp);
                adminRepository.save(user);

                newotp.setStatus(Otp.STATUS.INACTIVE.value);
                otpRepository.save(newotp);
                Duration duration = Duration.ofMinutes(5);
                String roleId = String.format(DIGIT_FORMAT, user.getRole());

                TokenGenerator.Token emailToken = tokenGenerator.create(PURPOSE_CHANGE_PASSWORD, email, roleId,
                        duration);
                return new EmailTokenView(OTP_VERIFIED, emailToken.value);

            } else {
                throw new BadRequestException("1051-Token is expired");
            }

        } else {
            throw new BadRequestException("1042-Incorrect otp");
        }

    }

    @Override
    public ResponseEntity<String> changePassword(@Valid ChangePasswordForm form) {

        Status status;
        try {
            status = tokenGenerator.verify(PURPOSE_CHANGE_PASSWORD, form.getEmailToken());

        } catch (InvalidTokenException e) {

            throw new BadRequestException(languageUtil.getTranslatedText(INVALID_TOKEN, null, "en"), e);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(INVALID_TOKEN1, null, "en"), e);

        }

        String email = status.data;
        Admin user = adminRepository.findByEmailId(email);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        adminRepository.save(user);
        String message = "password changed successfully";
        String json = String.format("{\"message\":\"%s\"}", message);
        return ResponseEntity.ok(json);

    }

    @Override
    public ResponseEntity<String> approveReject(Integer id, Integer btnValue) {
        User user = userRepository.findByIdAndRoleAndStatusIn(id, User.Role.VENDOR.value, vendorStatus).stream()
                .findFirst().orElseThrow(
                        () -> new BadRequestException(
                                languageUtil.getTranslatedText("invalid.id.provided", null, "en")));

        switch (btnValue) {
            case 0 -> {
                if (user.getStatus() == User.Status.STAGE2.value || user.getStatus() == User.Status.INACTIVE.value||user.getStatus()==User.Status.BLOCKED.value) {
                    user.setStatus(User.Status.ACTIVE.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Approval Granted", "Hi " + user.getUsername()
                            + "\n \t This is to inform you that your request for access to Rentx has been approved by the website administrator.\n \t You can now log in and begin using the website as per your access level.");
                } else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }
            }
            case 1 -> {
                if (user.getStatus() == User.Status.STAGE2.value) {
                    user.setStatus(User.Status.INACTIVE.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Rejection Confirmed", "Hi " + user.getUsername()
                            + "\n \t Thank you for your interest in accessing Rentx.\n \t Unfortunately, after careful consideration, we regret to inform you that your request for access has been denied by the website administrator.");

                } else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }
            }
            case 2 -> {
                if (user.getStatus() == User.Status.BLOCKED.value || user.getStatus() == User.Status.INACTIVE.value) {
                    user.setStatus(User.Status.DELETE.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Deleted Confirmed", "Hi " + user.getUsername()
                            + "\n \t Thank you for accessing Rentx.\n \t Unfortunately, after careful consideration, we regret to inform you that for access has been Deleted by the website administrator.");
                } else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }
            }
            case 3->{
                if(user.getStatus()==User.Status.ACTIVE.value){
                    user.setStatus(User.Status.BLOCKED.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Blocked Confirmed", "Hi " + user.getUsername()
                            + "\n \t Thank you for accessing Rentx.\n \t Unfortunately, after careful consideration, we regret to inform you that for access has been Deleted by the website administrator.");

                }
                else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }
            }
            default -> throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
        }

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public Pager<AdminVendorView> listVendor(String searchData, Integer page, Integer limit, String sort, String order,
            Integer statusValue) {

        try {
            page = Integer.parseInt(String.valueOf(page));
            limit = Integer.parseInt(String.valueOf(limit));
            statusValue = Integer.parseInt(String.valueOf(statusValue));

        } catch (NumberFormatException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
        }

        if (page < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Page.natual", null, "en"));
        }

        if (limit < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Size.natural", null, "en"));
        }
        if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            throw new BadRequestException(languageUtil.getTranslatedText("error.sort.direction.invalid", null, "en"));
        }
        
        Pager<AdminVendorView> viewVendor;
        boolean orderD = !order.equalsIgnoreCase("asc");
        List<AdminVendorView> vendorList;
        if (statusValue == 1) {
            vendorStatusAccept = new byte[] { User.Status.ACTIVE.value, User.Status.INACTIVE.value,User.Status.BLOCKED.value };
        } else {
            vendorStatusAccept = new byte[] { User.Status.STAGE2.value };
        }
        searchData = searchData.trim();

        vendorList = StreamSupport
                .stream(userRepository.findByRoleAndStatusInAndUsernameContainingIgnoreCase(User.Role.VENDOR.value,
                        vendorStatusAccept,
                        searchData,
                        PageRequest.of(page - 1, limit, (orderD == true) ? Sort.Direction.DESC : Sort.Direction.ASC,
                                sort))
                        .spliterator(), false)
                .map(AdminVendorView::new)
                .collect(Collectors.toList());
        int countData = userRepository.countByRoleAndStatusInAndUsernameContainingIgnoreCase(User.Role.VENDOR.value,
                vendorStatusAccept,
                searchData);
        viewVendor = new Pager<>(limit, countData, page);
        viewVendor.setResult(vendorList);
        return viewVendor;
    }


    @Override
    public Pager<AdminVendorView>listUser(String searchData, Integer page, Integer size, String sort, String order) {



        if (searchData.contains("%")|| searchData.contains("&"))  {
            // Search parameter contains %, don't return any results
            Pager<AdminVendorView> viewUser = new Pager<>(size, 0, 1);
            viewUser.setResult(new ArrayList<>());
            return viewUser;
        }
        try {
            page = Integer.parseInt(String.valueOf(page));
            size = Integer.parseInt(String.valueOf(size));

        } catch (NumberFormatException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
        }

        if (page < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Page.natual", null, "en"));
        }

        if (size < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Size.natural", null, "en"));
        }
        Pager<AdminVendorView> viewUser;
        
        boolean orderD = !order.equalsIgnoreCase("asc");
        List<AdminVendorView> userList;


        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User>criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User>userRoot=criteriaQuery.from(User.class);
        List<Predicate>predicate=new ArrayList<>();

        Predicate predicateRole=criteriaBuilder.equal(userRoot.get("role"),User.Role.USER.value);


        Predicate predicateStatusActive=criteriaBuilder.equal(userRoot.get("status"),User.Status.ACTIVE.value );
        Predicate predicateStatusBlock=criteriaBuilder.equal(userRoot.get("status"),User.Status.BLOCKED.value );
        Predicate predicateStatus=criteriaBuilder.or(predicateStatusActive,predicateStatusBlock);

        if(!StringUtils.isEmpty(searchData)){
            searchData = searchData.trim();
            Predicate predicate1=criteriaBuilder.like(criteriaBuilder.lower(userRoot.get("username")),"%"+searchData+"%");
            Predicate predicate2=criteriaBuilder.like(criteriaBuilder.lower(userRoot.get("email")),"%"+searchData+"%");
            Predicate predicate3=criteriaBuilder.like(criteriaBuilder.lower(userRoot.get("phone")),"%"+searchData+"%");
            Predicate predicateSearchOr=criteriaBuilder.or(predicate2,predicate1,predicate3);
            predicate.add(predicateSearchOr);

        }
        predicate.add(predicateRole);
        predicate.add(predicateStatus);

        Predicate And = criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
        criteriaQuery.where(And);

        if (sort != null) {

                if (order.equalsIgnoreCase("asc")) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(sort)));
                } else if (order.equalsIgnoreCase("desc")) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(sort)));
                }
                else{
                    throw new BadRequestException(languageUtil.getTranslatedText("error.sort.direction.invalid", null, "en"));
                }
        }
        else {
            throw new BadRequestException(languageUtil.getTranslatedText("item.invalid.sort", null, "en"));
        }
        TypedQuery<User>list=entityManager.createQuery(criteriaQuery);
        list.setFirstResult((page - 1) * size);
        list.setMaxResults(size);
        Long count;
        CriteriaQuery<Long> userCound = criteriaBuilder.createQuery(Long.class);
        Root<User>userquery=userCound.from(criteriaQuery.getResultType());
        userCound.where(And);
        userCound.select(criteriaBuilder.count(userquery));
        count=entityManager.createQuery(userCound).getSingleResult();

        Page<User>result=new PageImpl<>(list.getResultList(),
                PageRequest.of(page-1,size,(orderD == true) ? Sort.Direction.DESC : Sort.Direction.ASC,sort),count);

        userList=StreamSupport
                .stream(result.spliterator(),false)
                .map(AdminVendorView::new)
                .collect(Collectors.toList());
        viewUser=new Pager<>(size,count.intValue(),page);
        viewUser.setResult(userList);
        return viewUser;
    }

    @Override
    public ResponseEntity<String> UserBlockUnblock(Integer id, Integer btnValue){
        User user = userRepository.findByIdAndRoleAndStatusIn(id, User.Role.USER.value, vendorStatus).stream()
                .findFirst().orElseThrow(
                        () -> new BadRequestException(
                                languageUtil.getTranslatedText("invalid.id.provided", null, "en")));

        switch (btnValue) {
            case 0 -> {
                if(user.getStatus()==User.Status.BLOCKED.value){
                    user.setStatus(User.Status.ACTIVE.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:ReActivated Confirmed", "Hi " + user.getUsername()
                            +"\n \t This is to inform you that your request for access to Rentx has been ReActivated by the website administrator.\n \t You can now log in and begin using the website as per your access level.");
                }else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }

            }
            case 1 -> {
                if(user.getStatus()==User.Status.ACTIVE.value){
                    user.setStatus(User.Status.BLOCKED.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Blocked Confirmed", "Hi " + user.getUsername()
                            + "\n \t Thank you for accessing Rentx.\n \t Unfortunately, after careful consideration, we regret to inform you that for access has been temporarily Blocked by the website administrator.");

                }else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }

            }
            case 2 -> {
                if(user.getStatus()==User.Status.BLOCKED.value){
                    user.setStatus(User.Status.DELETE.value);
                    emailUtil.sendEmail(user.getEmail(), "Vendor Approval Process:Admin's Deleted Confirmed", "Hi " + user.getUsername()
                            + "\n \t Thank you for accessing Rentx.\n \t Unfortunately, after careful consideration, we regret to inform you that for access has been Deleted by the website administrator.");
                }else {
                    throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
                }

            }

            default -> throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
        }

        userRepository.save(user);
        return ResponseEntity.ok().build();



    }



}
