package com.innovature.rentx.view;

import com.innovature.rentx.entity.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Getter
@NoArgsConstructor(force = true)
public class AdminView {

    private final int id;
    private final String email;
    private final int status;
    private final int role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public AdminView(Admin admin){
        this.id=admin.getId();
        this.email=admin.getEmail();
        this.role=admin.getRole();
        this.status=admin.getStatus();
        this.createdAt = admin.getCreatedAt();
        this.updatedAt = admin.getUpdatedAt();


    }
}
