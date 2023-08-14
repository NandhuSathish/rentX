package com.innovature.rentx.entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
public class Category {
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
    private String coverImage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Byte status=STATUS.ACTIVE.value;
}