package com.innovature.rentx.view;

import com.innovature.rentx.entity.VendorDetails;

import lombok.Getter;

@Getter
public class VendorDetailsView {

    private final String accountNumber;
    private final String holderName;
    private final String ifsc;
    private final String gst;
    private final String pan;


    public VendorDetailsView(VendorDetails vendor){
        this.accountNumber=vendor.getAccountNumber();
        this.holderName=vendor.getHolderName();
        this.ifsc=vendor.getIfsc();
        this.gst=vendor.getGst();
        this.pan=vendor.getPan();
    }


    public Object getVendorDetails() {
        return null;
    }




}



