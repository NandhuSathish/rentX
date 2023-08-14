package com.innovature.rentx.view;

import com.innovature.rentx.entity.Admin;
import com.innovature.rentx.json.Json;
import com.innovature.rentx.security.util.TokenGenerator;
import lombok.Getter;

import java.util.Date;


@Getter
public class AdminLoginView extends AdminView{
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
    public AdminLoginView(Admin admin, TokenGenerator.Token accessToken, TokenGenerator.Token refreshToken) {
        super(admin);
        this.accessToken = new TokenView(accessToken);
        this.refreshToken = new TokenView(refreshToken);
    }



    public AdminLoginView(Admin admin, TokenView accessToken, TokenView refreshToken) {
        super(admin);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
