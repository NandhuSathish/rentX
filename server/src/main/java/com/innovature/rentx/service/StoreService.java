package com.innovature.rentx.service;

import com.innovature.rentx.form.StoreForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.StoreView;
import com.innovature.rentx.view.VendorStoreListView;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import javax.validation.Valid;


public interface StoreService {
    ResponseEntity<String> addStore(@Valid StoreForm form);

    Pager<VendorStoreListView>listStore(String search,Integer page,Integer size,String sort,String direction);

    Collection<StoreView> list();



}
