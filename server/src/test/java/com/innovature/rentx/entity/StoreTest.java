package com.innovature.rentx.entity;

import com.innovature.rentx.form.StoreForm;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StoreTest {

    @Test
    public void testCreateStore() {
        // Create a mock user
        User user = new User();
        user.setId(1);

        // Create a store form
        StoreForm storeForm = new StoreForm();
        storeForm.setName("Test Store");
        storeForm.setMobile("1234567890");
        storeForm.setPincode("12345");
        storeForm.setCity("Test");
        storeForm.setState("Test");
        storeForm.setLattitude("12.3456");
        storeForm.setLongitude("98.7654");
        storeForm.setBuildingName("Test Building");
        storeForm.setRoadName("Test Road");

        // Create a store entity
        Store store = new Store(storeForm, user);
        store.setId(1);
        store.setCreatedAt(new Date());
        store.setUpdatedAt(new Date());

        // Assertions
        assertNotNull(store);
        assertEquals(user, store.getUser());
        assertEquals("Test Store", store.getName());
        assertEquals("1234567890", store.getMobile());
        assertEquals("12345", store.getPincode());
        assertEquals("Test", store.getCity());
        assertEquals("Test", store.getState());
        assertEquals("12.3456", store.getLattitude());
        assertEquals("98.7654", store.getLongitude());
        assertEquals("Test Building", store.getBuildingName());
        assertEquals("Test Road", store.getRoadName());
        assertEquals(Store.Status.ACTIVE.value, store.getStatus());
        assertNotNull(store.getCreatedAt());
        assertNotNull(store.getUpdatedAt());
    }
}
