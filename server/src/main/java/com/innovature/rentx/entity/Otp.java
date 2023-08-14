package com.innovature.rentx.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="otp_table")
public class Otp {

    public static enum STATUS {

        ACTIVE((byte) 0),
        INACTIVE((byte) 1);

        public final byte value;

        private STATUS(byte value) {this.value = value; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String otp;

    private LocalTime expiry;

    private byte status;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;



}
