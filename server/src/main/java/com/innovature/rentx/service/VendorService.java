package com.innovature.rentx.service;

import javax.validation.Valid;

import com.innovature.rentx.form.VendorDetailsForm;

import com.innovature.rentx.view.VendorDetailsView;

public interface VendorService {

	VendorDetailsView add(@Valid VendorDetailsForm form);


}
