// package com.innovature.rentx.view;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;

// import com.innovature.rentx.entity.Category;

// import java.time.LocalDate;
// import java.util.ArrayList;

// import org.junit.Test;

// public class CategoryListViewTest {
//     /**
//      * Method under test: {@link CategoryListView#CategoryListView()}
//      */
//     @Test
//     public void testConstructor() {
//         CategoryListView actualCategoryListView = new CategoryListView();
//         assertNull(actualCategoryListView.getId());
//         assertNull(actualCategoryListView.getName());
//     }

//     /**
//      * Method under test: {@link CategoryListView#CategoryListView(Category)}
//      */
//     @Test
//     public void testConstructor2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         CategoryListView actualCategoryListView = new CategoryListView(category);
//         assertEquals(1, actualCategoryListView.getId().intValue());
//         assertEquals("Name", actualCategoryListView.getName());
//     }
// }

