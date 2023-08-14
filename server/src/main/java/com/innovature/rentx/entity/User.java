package com.innovature.rentx.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="user_table")
public class User {

    public static enum Status {
        ACTIVE((byte) 0),
        UNVERIFIED((byte) 1),
        DELETE((byte) 2),
        VERIFIED((byte)3),
        STAGE1((byte)4),
        STAGE2((byte)5),
        INACTIVE((byte) 6),
        BLOCKED((byte) 7),
        ;

        public final byte value;

        private Status(byte value) {
            this.value = value;
        }
    }

    public static enum Role {

        USER((byte) 0),
        VENDOR((byte) 1);

        public final byte value;

        private Role(byte value) {this.value = value; }
    }

    public static enum Type {

        NORMAL((byte) 0),
        GOOGLE((byte) 1);

        public final byte value;

        private Type(byte value) {this.value = value; }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String username;
    private byte role;
    private byte type;
    private byte status;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public  User(){
    }

    public  User(Integer id){
        this.id=id;
    }

//for Common user
    public  User(String email,String password){
        this.email=email;
        this.password=password;

        this.status = Status.UNVERIFIED.value;
        this.role=Role.USER.value;
        this.type=Type.NORMAL.value;


    }
    


    //for Google Sign in user
    public  User(String email){
        this.email=email;    

        this.status = Status.ACTIVE.value;
        this.role=Role.USER.value;
        this.type=Type.GOOGLE.value;


    }


}