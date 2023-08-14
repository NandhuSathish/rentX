package com.innovature.rentx.form;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategoryFormTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link CategoryForm}
     *   <li>{@link CategoryForm#setName(String)}
     *   <li>{@link CategoryForm#getName()}
     * </ul>
     */
    @Test
    public void testConstructor() {
        CategoryForm actualCategoryForm = new CategoryForm();
        actualCategoryForm.setName("Name");
        assertEquals("Name", actualCategoryForm.getName());
    }
}

