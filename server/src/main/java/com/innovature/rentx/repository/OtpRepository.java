package com.innovature.rentx.repository;
import org.springframework.data.repository.Repository;
import com.innovature.rentx.entity.Otp;

public interface OtpRepository extends Repository<Otp, Integer>{
    Otp save(Otp otp);
    Otp findByEmail(String email);
}
