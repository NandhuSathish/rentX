package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.Store;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.entity.Store.Status;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.exception.ConflictException;
import com.innovature.rentx.form.StoreForm;
import com.innovature.rentx.repository.StoreRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.util.SecurityUtil;
import com.innovature.rentx.service.StoreService;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AdminVendorView;
import com.innovature.rentx.view.StoreView;
import com.innovature.rentx.view.VendorStoreListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageUtil languageUtil;

    private static final String UNABLE_PERFORM = "unable.to.perform.this.action";

    @Override
    public ResponseEntity<String> addStore(StoreForm form) {

        User user = userRepository.findByIdAndStatus(SecurityUtil.getCurrentUserId(),User.Status.ACTIVE.value);
        while(user!=null){
       
        if (storeRepository.findByNameAndLattitudeAndLongitude(form.getName(),form.getLattitude(),form.getLongitude())!= null) {
            throw new ConflictException(languageUtil.getTranslatedText("store.already.exists", null, "en"));
        }
        else{
            storeRepository.save(new Store(form, user));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
    }
    throw new ConflictException(languageUtil.getTranslatedText("unable.to.perform.this.action", null, "en"));
    }


    public Pager<VendorStoreListView>listStore(String search,Integer page,Integer size,String sort,String direction){

        search = search.trim();
        try{
            page=Integer.parseInt(String.valueOf(page));
            size=Integer.parseInt(String.valueOf(size));

        }
        catch (NumberFormatException e){
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_PERFORM, null, "en"));
        }

        if (page < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Page.natual", null, "en"));
        }

        if (size < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Size.natural", null, "en"));
        }
        Pager<VendorStoreListView>viewStore;
        boolean directionSort = !direction.equalsIgnoreCase("asc");
        List<VendorStoreListView>storeList;
        storeList= StreamSupport.stream(storeRepository.findByUserIdAndUserRoleAndUserStatusAndNameContainingIgnoreCase(
                SecurityUtil.getCurrentUserId(),
                        User.Role.VENDOR.value,
                        User.Status.ACTIVE.value,
                        search,PageRequest.of(page - 1, size, (directionSort == true) ? Sort.Direction.DESC : Sort.Direction.ASC,sort)).spliterator(), false)
                .map(VendorStoreListView::new)
                .collect(Collectors.toList());
        int countData=storeRepository.countByUserIdAndUserRoleAndUserStatusAndNameContainingIgnoreCase(SecurityUtil.getCurrentUserId(),
                User.Role.VENDOR.value,
                User.Status.ACTIVE.value,
                search);
        viewStore=new Pager<>(size,countData,page);
        viewStore.setResult(storeList);
        return viewStore;

    }


    @Override
    public Collection<StoreView> list() {
        Iterable<Store> iterableCategories = storeRepository.findAllByStatus(Status.ACTIVE.value);
    
        // Map Category objects to CategoryListView objects using Stream API and Collectors.toList()
        List<StoreView> storeView = StreamSupport.stream(iterableCategories.spliterator(), false)
                                                               .map(store -> new StoreView(store))
                                                               .collect(Collectors.toList());
        return storeView;
    }

    
}
