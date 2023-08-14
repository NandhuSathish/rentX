package com.innovature.rentx.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="admin_table")
public class Admin {

    public static enum Role {

        ADMIN((byte) 2);
        public final byte value;

        private Role(byte value) {this.value = value; }
    }

    public static enum Status {
        ACTIVE((byte) 0),
        INACTIVE((byte) 1),
        DELETE((byte) 2);

        public final byte value;

        private Status(byte value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;
    private String password;

    private byte role;
    private byte status;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Admin(String email,String password){
        this.email=email;
        this.password=password;
        this.role= Role.ADMIN.value;
        this.status = User.Status.UNVERIFIED.value;

        Date dt = new Date();
        this.createdAt = dt;
        this.updatedAt = dt;
    }
}

