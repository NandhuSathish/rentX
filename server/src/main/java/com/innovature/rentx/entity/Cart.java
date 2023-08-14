package com.innovature.rentx.entity;

import com.innovature.rentx.form.CartForm;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {



    public enum Status{
        ACTIVE((byte)0),
        INACTIVE((byte)1),
        DELETED((byte)2);

        public  final byte value;
        private  Status(byte value) {this.value = value;}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;




    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name =  "productId", referencedColumnName = "id")
    private Product product;

    @ManyToOne(optional = false,fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Integer quantity;



    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private byte status;
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;




    public Cart(CartForm form,Product product,User user){

        this.product=product;
        this.user=user;
        this.quantity= form.getQuantity();
        this.startDate=form.getStartDate();
        this.endDate=form.getEndDate();

    }



}
