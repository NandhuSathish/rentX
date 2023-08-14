package com.innovature.rentx.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.json.Json;
import com.innovature.rentx.security.util.TokenGenerator;

import lombok.Getter;

import java.util.Date;

@Getter
@JsonInclude(Include.NON_NULL)
public class LoginView extends UserView {

    private String emailToken;
    private TokenView accessToken;
    private TokenView refreshToken;
    private String message;
    private String msg;

    public static class TokenView {

        private final String value;
        @Json.DateTimeFormat
        private final Date expiry;

        public TokenView(TokenGenerator.Token token) {
            this.value = token.value;
            this.expiry = new Date(token.expiry);
        }

        public TokenView(String value, long expiry) {
            this.value = value;
            this.expiry = new Date(expiry);
        }

        public String getValue() {
            return value;
        }

        public Date getExpiry() {
            return expiry;
        }
    }

    public LoginView(User user) {
        super(user);
    }

    public LoginView(User user, TokenGenerator.Token accessToken, TokenGenerator.Token refreshToken) {
        super(user);
        this.accessToken = new TokenView(accessToken);
        this.refreshToken = new TokenView(refreshToken);
    }

    public LoginView(User user, String emailToken) {
        super(user);
        this.emailToken = emailToken;
    }

    public LoginView(User user, TokenView accessToken, TokenView refreshToken) {
        super(user);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public LoginView(User user, String message, String msg) {
        super(user);
        this.message = message;
        this.msg = msg;

    }

}
