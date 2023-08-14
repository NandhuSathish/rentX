// package com.innovature.rentx.view;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;

// import com.innovature.rentx.entity.Category;
// import com.innovature.rentx.entity.SubCategory;

// import java.time.LocalDate;
// import java.util.ArrayList;

// import org.junit.Test;

// public class SubCategoryViewTest {
//     /**
//      * Method under test: {@link SubCategoryView#SubCategoryView()}
//      */
//     @Test
//     public void testConstructor() {
//         SubCategoryView actualSubCategoryView = new SubCategoryView();
//         assertNull(actualSubCategoryView.getId());
//         assertNull(actualSubCategoryView.getMessage());
//     }

//     /**
//      * Method under test: {@link SubCategoryView#SubCategoryView(SubCategory, String)}
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

//         SubCategory subCategory = new SubCategory();
//         subCategory.setCategory(category);
//         subCategory.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory.setId(1);
//         subCategory.setName("Name");
//         subCategory.setStatus((byte) 'A');
//         subCategory.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         SubCategoryView actualSubCategoryView = new SubCategoryView(subCategory, "Not all who wander are lost");

//         assertEquals(1, actualSubCategoryView.getId().intValue());
//         assertEquals("Not all who wander are lost", actualSubCategoryView.getMessage());
//     }
// }

