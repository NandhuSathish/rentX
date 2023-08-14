package com.innovature.rentx.view;

import lombok.Getter;

@Getter
public class VendorOtpView {
    private final String message;
    private final String emailToken;
    private final  Byte status;
    private final  Byte role;


    public VendorOtpView(String msg, String emailToken, byte status, byte role) {
        this.message=msg;
        this.emailToken = emailToken;
        this.status=status;
        this.role = role;

        
    }

    public void setToken(String string) {
    }


}
