package com.innovature.rentx.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;

@Getter
@Setter
@Entity
public class SubCategory {
    public static enum STATUS {

        ACTIVE((byte) 0),
        DELETED((byte) 1);

        public final byte value;

        private STATUS(byte value) {this.value = value; }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Byte status=STATUS.ACTIVE.value;
}
