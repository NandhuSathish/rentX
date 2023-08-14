package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.entity.VendorDetails;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.VendorDetailsForm;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.repository.VendorDetailsRepository;
import com.innovature.rentx.service.VendorService;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.view.VendorDetailsView;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovature.rentx.security.util.InvalidTokenException;
import com.innovature.rentx.security.util.TokenExpiredException;
import com.innovature.rentx.security.util.TokenGenerator;
import com.innovature.rentx.security.util.TokenGenerator.Status;

@Service
public class VendorServiceImpl implements VendorService {

    private static final String PURPOSE_VENDOR_REG_STAGE_2 = "PURPOSE_VENDOR_REG_STAGE_2";

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private UserRepository userRepository;

    

    @Autowired
    private VendorDetailsRepository vendorRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    public VendorServiceImpl(UserRepository userRepository2, VendorDetailsRepository vendorDetailsRepository,
            LanguageUtil languageUtil2, TokenGenerator tokenGenerator2) {
    }

    @Override
    public VendorDetailsView add(VendorDetailsForm form) throws BadRequestException{

    Status status;
    try{
        status=tokenGenerator.verify(PURPOSE_VENDOR_REG_STAGE_2, form.getEmailToken());
       
    }catch (InvalidTokenException e) {
        throw new BadRequestException(languageUtil.getTranslatedText("Invalid.Token.Email", null, "en"), e);
    } catch (TokenExpiredException e) {
        throw new BadRequestException(languageUtil.getTranslatedText("Token.expired", null, "en"), e);
    }
    String email = status.data;
    User user=userRepository.findByEmailId(email);

    if(user.getStatus()==User.Status.STAGE1.value){
    
        VendorDetails vendorDetails=new VendorDetails();
        vendorDetails.setAccountNumber(form.getAccountNumber());
        vendorDetails.setHolderName(form.getHolderName());
        vendorDetails.setIfsc(form.getIfsc());
        vendorDetails.setPan(form.getPan());
        vendorDetails.setGst(form.getGst());
        vendorDetails.setUserId(user.getId());
        Date dt = new Date();    
        vendorDetails.setCreatedAt(dt);
        vendorDetails.setUpdatedAt(dt);  

              
        vendorRepository.save(vendorDetails);

        user.setStatus(User.Status.STAGE2.value); 

        userRepository.save(user);
    
        return new VendorDetailsView(vendorDetails);   
    }
    else
        throw new BadRequestException(languageUtil.getTranslatedText("unable.to.perform.this.action", null, "en"));
              
           
    }

}
