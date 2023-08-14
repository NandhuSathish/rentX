// package com.innovature.rentx.view;

// import static org.junit.Assert.assertEquals;

// import com.innovature.rentx.entity.Category;
// import com.innovature.rentx.entity.SubCategory;

// import java.time.LocalDate;
// import java.util.ArrayList;

// import org.junit.Test;

// public class SubCategoryListViewTest {
//     /**
//      * Method under test: {@link SubCategoryListView#SubCategoryListView(Integer, String)}
//      */
//     @Test
//     public void testConstructor() {
//         SubCategoryListView actualSubCategoryListView = new SubCategoryListView(1, "Name");

//         assertEquals(1, actualSubCategoryListView.getId().intValue());
//         assertEquals("Name", actualSubCategoryListView.getName());
//     }

//     /**
//      * Method under test: {@link SubCategoryListView#SubCategoryListView(SubCategory)}
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
//         SubCategoryListView actualSubCategoryListView = new SubCategoryListView(subCategory);
//         assertEquals(1, actualSubCategoryListView.getId().intValue());
//         assertEquals("Name", actualSubCategoryListView.getName());
//     }
// }

