package com.innovature.rentx.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AllSubCategoryListViewTest {
    /**
     * Method under test: {@link AllSubCategoryListView#AllSubCategoryListView(String, List)}
     */
    @Test
    public void testConstructor() {
        ArrayList<SubCategoryListView> subcategories = new ArrayList<>();
        AllSubCategoryListView actualAllSubCategoryListView = new AllSubCategoryListView("Category", subcategories);

        assertEquals("Category", actualAllSubCategoryListView.getCategory());
        List<SubCategoryListView> subcategories2 = actualAllSubCategoryListView.getSubcategories();
        assertSame(subcategories, subcategories2);
        assertTrue(subcategories2.isEmpty());
    }
}

