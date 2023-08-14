package com.innovature.rentx.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.innovature.rentx.form.ImageProductForm;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product_image")
public class ImageProduct {

    
    public enum Status{
        ACTIVE((byte)0),
        INACTIVE((byte)1),
        DELETED((byte)2);

        public  final byte value;
        private  Status(byte value) {this.value = value;}
    }

    @Id
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Integer productId;
    
    @Column(nullable = false)
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private byte status=Status.ACTIVE.value;


    public ImageProduct(){}

    public ImageProduct(Integer id){
        this.productId=id;
    }

    public ImageProduct(ImageProductForm form) {
        this.productId=form.getProductId();
        this.image1=form.getImage1();
        this.image2=form.getImage2();
        this.image3=form.getImage3();
        this.image4=form.getImage4();
    }

}
