package com.innovature.rentx.view;

import lombok.Getter;

@Getter
public class VendorRegStage1View {

    private final String username;
    private final String phone;
    private final String emailToken;

    public VendorRegStage1View(String username,String phone,String vendorEmailToken){
        this.username=username;
        this.phone=phone;
        this.emailToken=vendorEmailToken;
    }

  
    
}
