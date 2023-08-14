package com.innovature.rentx.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="vendor_details")
public class VendorDetails {


    @Id
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Integer userId;

    private String accountNumber;
    private String holderName;
    private String ifsc;
    private String gst;
    private String pan;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public VendorDetails(){

    }

    public VendorDetails(Integer id){
        this.userId=id;
        
    }
  
    
}
