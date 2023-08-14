package com.innovature.rentx.view;


import com.innovature.rentx.entity.ImageProduct;
import com.innovature.rentx.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProductImageView {

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;


    public UserProductImageView(ImageProduct image, Product product){
        this.image1=product.getCoverImage();
        this.image2=image.getImage1();
        this.image3=image.getImage2();
        this.image4=image.getImage3();
        this.image5=image.getImage4();

    }
}
