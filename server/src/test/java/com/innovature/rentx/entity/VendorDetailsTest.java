
package com.innovature.rentx.entity;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VendorDetailsTest {

    @Test
    public void testCreateVendorDetails() {
        // Create a vendor details entity
        VendorDetails vendorDetails = new VendorDetails();
        vendorDetails.setUserId(1);
        vendorDetails.setAccountNumber("1234567890");
        vendorDetails.setHolderName("John Doe");
        vendorDetails.setIfsc("ABCD1234");
        vendorDetails.setGst("GST123");
        vendorDetails.setPan("PAN123");
        vendorDetails.setCreatedAt(new Date());
        vendorDetails.setUpdatedAt(new Date());

        // Assertions
        assertNotNull(vendorDetails);
        assertEquals(1, vendorDetails.getUserId());
        assertEquals("1234567890", vendorDetails.getAccountNumber());
        assertEquals("John Doe", vendorDetails.getHolderName());
        assertEquals("ABCD1234", vendorDetails.getIfsc());
        assertEquals("GST123", vendorDetails.getGst());
        assertEquals("PAN123", vendorDetails.getPan());
        assertNotNull(vendorDetails.getCreatedAt());
        assertNotNull(vendorDetails.getUpdatedAt());
    }
}
