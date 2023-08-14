package com.innovature.rentx.repository;
import org.springframework.data.repository.Repository;

import com.innovature.rentx.entity.VendorDetails;

public interface VendorDetailsRepository extends Repository<VendorDetails,Integer>  {
    
    VendorDetails save(VendorDetails vendor);
}
