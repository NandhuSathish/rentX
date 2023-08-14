package com.innovature.rentx.view;

import com.innovature.rentx.entity.Store;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StoreView {
    
    private Integer id;
    private String name;
    
    public StoreView(Store store)
    {
        this.id = store.getId();
        this.name = store.getName();
    }

    public StoreView() {
    }
}