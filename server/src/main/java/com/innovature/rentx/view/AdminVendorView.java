package com.innovature.rentx.view;

import com.innovature.rentx.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(force = true)
public class AdminVendorView {

    private final Integer id;
    private final String email;
    private final String phone;

    private final String username;
    private final byte status;
    private final byte role;
    private final Date createdAt;
    private final Date updatedAt;

    public AdminVendorView(User user){
        this.id=user.getId();
        this.email=user.getEmail();
        this.phone=user.getPhone();
        this.username=user.getUsername();
        this.status=user.getStatus();
        this.role=user.getRole();
        this.createdAt=user.getCreatedAt();
        this.updatedAt=user.getUpdatedAt();

    }







}
