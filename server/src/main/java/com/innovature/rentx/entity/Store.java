package com.innovature.rentx.entity;

import com.innovature.rentx.form.StoreForm;
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
@Table(name = "store_table")
@Entity
public class Store {




    public  static  enum Status{
        ACTIVE((byte)0),
        INACTIVE((byte)1),
        DELETED((byte)2);

        public  final byte value;
        private  Status(byte value) {this.value = value;}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false,fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String mobile;
    private String name;
    private String pincode;
    private String city;
    private String state;
    private String lattitude;
    private  String longitude;
    private String buildingName;
    private  String roadName;
    private byte status=Status.ACTIVE.value;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Store(Integer id){
        this.id=id;
    }

    public Store(StoreForm form, User user) {
        this.user = user;
        this.name=form.getName();
        this.mobile = form.getMobile();
        this.pincode = form.getPincode();
        this.city = form.getCity();
        this.state = form.getState();
        this.lattitude = form.getLattitude();
        this.longitude = form.getLongitude();
        this.buildingName = form.getBuildingName();
        this.roadName = form.getRoadName();
        Date dt = new Date();
        this.createdAt = dt;
        this.updatedAt = dt;



    }
}
