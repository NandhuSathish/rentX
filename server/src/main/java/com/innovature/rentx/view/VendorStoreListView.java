package com.innovature.rentx.view;

import com.innovature.rentx.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class VendorStoreListView {
    private final int id;
    private final int user;

    private final String name;
    private final String mobile;
    private final String pincode;
    private final String city;
    private final Date createdAt;
    private final Date updatedAt;


    public VendorStoreListView(Store store){
        this.id=store.getId();
        this.name=store.getName();
        this.mobile=store.getMobile();
        this.pincode=store.getPincode();
        this.city=store.getCity();
        this.createdAt=store.getCreatedAt();
        this.updatedAt=store.getUpdatedAt();
        this.user=store.getUser().getId();

    }





}
