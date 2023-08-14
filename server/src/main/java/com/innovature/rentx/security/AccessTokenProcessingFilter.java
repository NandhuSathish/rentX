package com.innovature.rentx.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessTokenProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Pattern AUTH_PATTERN = Pattern.compile("rentx ([0-9a-f]+)");

    public AccessTokenProcessingFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String authHeader = request.getHeader("authorization");
        if (authHeader == null) {
            return null;
        }

        Matcher matcher = AUTH_PATTERN.matcher(authHeader);
        if (!matcher.matches()) {
            return null;
        }

        return matcher.group(1);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return AccessTokenUserDetailsService.PURPOSE_ACCESS_TOKEN;
    }
}
