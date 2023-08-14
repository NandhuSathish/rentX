package com.innovature.rentx.security;

import com.innovature.rentx.security.util.TokenExpiredException;
import com.innovature.rentx.entity.Admin;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.repository.AdminRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.util.InvalidTokenException;
import com.innovature.rentx.security.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AccessTokenUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    public static final String PURPOSE_ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (!PURPOSE_ACCESS_TOKEN.equals(token.getCredentials())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        final TokenGenerator.Status status;
        User loggedUser = new User();

        try {
            status = tokenGenerator.verify(PURPOSE_ACCESS_TOKEN, token.getPrincipal().toString());

            // loggedUser = userRepository.findByIdAndStatus(Integer.parseInt(status.data.trim()),
            //         User.Status.ACTIVE.value);

            // if (loggedUser == null) {

            //     throw new UsernameNotFoundException("user not found");

            // }

        } catch (InvalidTokenException e) {
            throw new UsernameNotFoundException("Invalid access token", e);
        } catch (TokenExpiredException e) {
            throw new UsernameNotFoundException("Access token expired", e);
        }

        int userId = Integer.parseInt(status.data.trim());
        int roleId = Integer.parseInt(status.roleId.trim());

        if (roleId == 2) {
            Admin admin = adminRepository.findByUserId(userId);
            return new AccessTokenUserDetails(userId, admin.getRole());
        } else {
            User user = userRepository.findByUserId(userId);
            return new AccessTokenUserDetails(userId, user.getRole());
        }
    }
}
