package com.innovature.rentx.util;

import org.springframework.stereotype.Component;

@Component
public class EmailContents {

    public String otpEmailContent(String otp) {

        String emailContent = "<div style=\"color: black;\">" +
                "You are just a step away from accessing your RentX account.<br>\n" + "<br>\n" +
                "We are sharing a verification code to access your account. The code is valid for 2 minutes and <br>\n"
                + "<br>\n" +
                "usable only once.<br>\n" + "<br>\n" +
                "Once you have verified the code, your account will be verified.<br>\n" + "<br>\n" +
                "This is to ensure that only you have access to your account.<br>\n" +
                "Your OTP :" +
                "<div style=\" border-radius: 4px; display: inline-block; padding: 8px;\">" +
                "<span style=\"font-size: 24px; font-weight: bold;\">" + otp + "</span>" +
                "</div>" + "<br>\n" +
                "Expires in: 2 minutes<br>\n" + "<br>\n" + "<br>\n" +
                "</div>";
        return emailContent;

    }

}
