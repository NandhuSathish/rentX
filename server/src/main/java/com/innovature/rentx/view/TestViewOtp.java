package com.innovature.rentx.view;



import lombok.Getter;

@Getter
public class TestViewOtp{

    private final String message;
    private final String emailToken;
    private final String otp;
    
    public TestViewOtp(String msg, String emailToken,String otp) {
        this.message=msg;
        this.emailToken = emailToken;
        this.otp=otp;
        
    }


}
