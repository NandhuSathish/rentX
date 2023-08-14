package com.innovature.rentx.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SubCategoryFormTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link SubCategoryForm}
     *   <li>{@link SubCategoryForm#setCategoryId(String)}
     *   <li>{@link SubCategoryForm#setName(String)}
     *   <li>{@link SubCategoryForm#getCategoryId()}
     *   <li>{@link SubCategoryForm#getName()}
     * </ul>
     */
    @Test
    public void testConstructor() {
        SubCategoryForm actualSubCategoryForm = new SubCategoryForm();
        actualSubCategoryForm.setCategoryId("42");
        actualSubCategoryForm.setName("Name");
        assertEquals("42", actualSubCategoryForm.getCategoryId());
        assertEquals("Name", actualSubCategoryForm.getName());
    }
}

