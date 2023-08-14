package com.innovature.rentx.view;

import com.innovature.rentx.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Getter
@Setter
@NoArgsConstructor(force = true)
public class UserView {

    private int id;
    private String email;
    private int status;
    private int role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public UserView(User user){
        this.id=user.getId();
        this.email=user.getEmail();
        this.role=user.getRole();
        this.status=user.getStatus();

        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();


    }

    public UserView(int id, String email, int status, int role) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public void setId(int i) {
        this.id = i;

    }

    public void setEmail(String string) {
        this.email = string;

    }
    

    public void setStatus(int i) {
        this.status = i;

    }

    public void setRole(byte value) {
        this.role = value;

    }

   
}
