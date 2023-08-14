package com.innovature.rentx.view;

import com.innovature.rentx.json.Json;
import com.innovature.rentx.security.util.TokenGenerator;
import lombok.Getter;

import java.util.Date;

@Getter
public class RefreshTokenView {
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


    private final TokenView accessToken;
    private final TokenView refreshToken;

    public RefreshTokenView(TokenView accessToken,TokenView refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
