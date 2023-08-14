package com.innovature.rentx.security.util;
import com.innovature.rentx.security.AccessTokenUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof AccessTokenUserDetails)) {
            return null;
        }

        return ((AccessTokenUserDetails) principal).userId;
    }
    public static Integer getCurrentRoleID() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof AccessTokenUserDetails)) {
            return null;
        }

        return ((AccessTokenUserDetails) principal).roleId;
    }
}
