// package com.innovature.rentx.service.impl;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertSame;
// import static org.junit.Assert.assertThrows;
// import static org.junit.Assert.assertTrue;
// import static org.mockito.Mockito.anyByte;
// import static org.mockito.Mockito.anyInt;
// import static org.mockito.Mockito.atLeast;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.innovature.rentx.entity.Category;
// import com.innovature.rentx.entity.SubCategory;
// import com.innovature.rentx.exception.ConflictException;
// import com.innovature.rentx.form.CategoryForm;
// import com.innovature.rentx.repository.CategoryRepository;
// import com.innovature.rentx.repository.SubCategoryRepository;
// import com.innovature.rentx.util.CategoryUtil;
// import com.innovature.rentx.util.LanguageUtil;
// import com.innovature.rentx.view.CategoryListView;
// import com.innovature.rentx.view.CategoryView;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.Ignore;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// @ContextConfiguration(classes = {CategoryServiceImpl.class})
// @RunWith(SpringJUnit4ClassRunner.class)
// public class CategoryServiceImplTest {
//     @MockBean
//     private CategoryRepository categoryRepository;

//     @Autowired
//     private CategoryServiceImpl categoryServiceImpl;

//     @MockBean
//     private CategoryUtil categoryUtil;

//     @MockBean
//     private LanguageUtil languageUtil;

//     @MockBean
//     private SubCategoryRepository subCategoryRepository;

//     /**
//      * Method under test: {@link CategoryServiceImpl#registerCategory(CategoryForm)}
//      */
//     @Test
//     public void testRegisterCategory() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
//         doNothing().when(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         CategoryView actualRegisterCategoryResult = categoryServiceImpl.registerCategory(categoryForm);
//         assertNull(actualRegisterCategoryResult.getId());
//         assertEquals(UserServiceImpl.SUCCESSFULLY_CREATED, actualRegisterCategoryResult.getMessage());
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#registerCategory(CategoryForm)}
//      */
//     @Test
//     public void testRegisterCategory2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
//         doThrow(new ConflictException("Just cause")).when(categoryUtil)
//                 .checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         assertThrows(ConflictException.class, () -> categoryServiceImpl.registerCategory(categoryForm));
//         verify(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#updateCategory(Integer, CategoryForm)}
//      */
//     @Test
//     public void testUpdateCategory() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         CategoryView actualUpdateCategoryResult = categoryServiceImpl.updateCategory(1, categoryForm);
//         assertEquals(1, actualUpdateCategoryResult.getId().intValue());
//         assertEquals("successfully updated", actualUpdateCategoryResult.getMessage());
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#updateCategory(Integer, CategoryForm)}
//      */
//     @Test
//     public void testUpdateCategory2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doThrow(new ConflictException("Just cause")).when(categoryUtil)
//                 .checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doThrow(new ConflictException("Just cause")).when(categoryUtil)
//                 .requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         assertThrows(ConflictException.class, () -> categoryServiceImpl.updateCategory(1, categoryForm));
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#updateCategory(Integer, CategoryForm)}
//      */
//     @Test
//     public void testUpdateCategory3() {
//         Category category = mock(Category.class);
//         when(category.getId()).thenReturn(1);
//         doNothing().when(category).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(category).setId(Mockito.<Integer>any());
//         doNothing().when(category).setName(Mockito.<String>any());
//         doNothing().when(category).setStatus(Mockito.<Byte>any());
//         doNothing().when(category).setSubCategories(Mockito.<List<SubCategory>>any());
//         doNothing().when(category).setUpdatedAt(Mockito.<LocalDateTime>any());
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         CategoryView actualUpdateCategoryResult = categoryServiceImpl.updateCategory(1, categoryForm);
//         assertEquals(1, actualUpdateCategoryResult.getId().intValue());
//         assertEquals("successfully updated", actualUpdateCategoryResult.getMessage());
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(category).getId();
//         verify(category).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(category).setId(Mockito.<Integer>any());
//         verify(category, atLeast(1)).setName(Mockito.<String>any());
//         verify(category).setStatus(Mockito.<Byte>any());
//         verify(category).setSubCategories(Mockito.<List<SubCategory>>any());
//         verify(category).setUpdatedAt(Mockito.<LocalDateTime>any());
//         verify(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#updateCategory(Integer, CategoryForm)}
//      */
//     @Test
//     public void testUpdateCategory4() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         Category category2 = mock(Category.class);
//         when(category2.getId()).thenReturn(1);
//         doNothing().when(category2).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(category2).setId(Mockito.<Integer>any());
//         doNothing().when(category2).setName(Mockito.<String>any());
//         doNothing().when(category2).setStatus(Mockito.<Byte>any());
//         doNothing().when(category2).setSubCategories(Mockito.<List<SubCategory>>any());
//         doNothing().when(category2).setUpdatedAt(Mockito.<LocalDateTime>any());
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         doNothing().when(categoryUtil).checkDuplicateCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         assertThrows(ConflictException.class, () -> categoryServiceImpl.updateCategory(1, categoryForm));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(category2).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(category2).setId(Mockito.<Integer>any());
//         verify(category2).setName(Mockito.<String>any());
//         verify(category2).setStatus(Mockito.<Byte>any());
//         verify(category2).setSubCategories(Mockito.<List<SubCategory>>any());
//         verify(category2).setUpdatedAt(Mockito.<LocalDateTime>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#deleteCategory(Integer)}
//      */
//     @Test
//     public void testDeleteCategory() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByCategoryIdAndStatus(anyInt(), anyByte())).thenReturn(new ArrayList<>());
//         when(subCategoryRepository.saveAll(Mockito.<Iterable<SubCategory>>any())).thenReturn(new ArrayList<>());
//         CategoryView actualDeleteCategoryResult = categoryServiceImpl.deleteCategory(1);
//         assertEquals(1, actualDeleteCategoryResult.getId().intValue());
//         assertEquals("successfully deleted", actualDeleteCategoryResult.getMessage());
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByCategoryIdAndStatus(anyInt(), anyByte());
//         verify(subCategoryRepository).saveAll(Mockito.<Iterable<SubCategory>>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#deleteCategory(Integer)}
//      */
//     @Test
//     public void testDeleteCategory2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByCategoryIdAndStatus(anyInt(), anyByte()))
//                 .thenThrow(new ConflictException("Just cause"));
//         when(subCategoryRepository.saveAll(Mockito.<Iterable<SubCategory>>any()))
//                 .thenThrow(new ConflictException("Just cause"));
//         assertThrows(ConflictException.class, () -> categoryServiceImpl.deleteCategory(1));
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByCategoryIdAndStatus(anyInt(), anyByte());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#deleteCategory(Integer)}
//      */
//     @Test
//     public void testDeleteCategory3() {
//         Category category = mock(Category.class);
//         when(category.getId()).thenReturn(1);
//         doNothing().when(category).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(category).setId(Mockito.<Integer>any());
//         doNothing().when(category).setName(Mockito.<String>any());
//         doNothing().when(category).setStatus(Mockito.<Byte>any());
//         doNothing().when(category).setSubCategories(Mockito.<List<SubCategory>>any());
//         doNothing().when(category).setUpdatedAt(Mockito.<LocalDateTime>any());
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category2);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByCategoryIdAndStatus(anyInt(), anyByte())).thenReturn(new ArrayList<>());
//         when(subCategoryRepository.saveAll(Mockito.<Iterable<SubCategory>>any())).thenReturn(new ArrayList<>());
//         CategoryView actualDeleteCategoryResult = categoryServiceImpl.deleteCategory(1);
//         assertEquals(1, actualDeleteCategoryResult.getId().intValue());
//         assertEquals("successfully deleted", actualDeleteCategoryResult.getMessage());
//         verify(categoryRepository).save(Mockito.<Category>any());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(category).getId();
//         verify(category).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(category).setId(Mockito.<Integer>any());
//         verify(category).setName(Mockito.<String>any());
//         verify(category, atLeast(1)).setStatus(Mockito.<Byte>any());
//         verify(category).setSubCategories(Mockito.<List<SubCategory>>any());
//         verify(category).setUpdatedAt(Mockito.<LocalDateTime>any());
//         verify(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByCategoryIdAndStatus(anyInt(), anyByte());
//         verify(subCategoryRepository).saveAll(Mockito.<Iterable<SubCategory>>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#deleteCategory(Integer)}
//      */
//     @Test
//     public void testDeleteCategory4() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(categoryRepository.save(Mockito.<Category>any())).thenReturn(category);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         Category category2 = mock(Category.class);
//         when(category2.getId()).thenReturn(1);
//         doNothing().when(category2).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(category2).setId(Mockito.<Integer>any());
//         doNothing().when(category2).setName(Mockito.<String>any());
//         doNothing().when(category2).setStatus(Mockito.<Byte>any());
//         doNothing().when(category2).setSubCategories(Mockito.<List<SubCategory>>any());
//         doNothing().when(category2).setUpdatedAt(Mockito.<LocalDateTime>any());
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         doNothing().when(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");
//         when(subCategoryRepository.findByCategoryIdAndStatus(anyInt(), anyByte())).thenReturn(new ArrayList<>());
//         when(subCategoryRepository.saveAll(Mockito.<Iterable<SubCategory>>any())).thenReturn(new ArrayList<>());
//         assertThrows(ConflictException.class, () -> categoryServiceImpl.deleteCategory(1));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(category2).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(category2).setId(Mockito.<Integer>any());
//         verify(category2).setName(Mockito.<String>any());
//         verify(category2).setStatus(Mockito.<Byte>any());
//         verify(category2).setSubCategories(Mockito.<List<SubCategory>>any());
//         verify(category2).setUpdatedAt(Mockito.<LocalDateTime>any());
//         verify(categoryUtil).checkCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#getCategories(String, int, int, String, Sort.Direction)}
//      */
// //     @Test
// //     public void testGetCategories() {
// //         ArrayList<CategoryListView> categoryListViewList = new ArrayList<>();
// //         when(categoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any())).thenReturn(categoryListViewList);
// //         doNothing().when(categoryUtil)
// //                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //         List<CategoryListView> actualCategories = categoryServiceImpl.getCategories("Name", 10, 3, "Sort Field",
// //                 Sort.Direction.ASC);
// //         assertSame(categoryListViewList, actualCategories);
// //         assertTrue(actualCategories.isEmpty());
// //         verify(categoryRepository).findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any());
// //         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#getCategories(String, int, int, String, Sort.Direction)}
//      */
// //     @Test
// //     public void testGetCategories2() {
// //         when(categoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any())).thenReturn(new ArrayList<>());
// //         doThrow(new ConflictException("Just cause")).when(categoryUtil)
// //                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //         assertThrows(ConflictException.class,
// //                 () -> categoryServiceImpl.getCategories("Name", 10, 3, "Sort Field", Sort.Direction.ASC));
// //         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#getCategories(String, int, int, String, Sort.Direction)}
//      */
// //     @Test
// //     @Ignore("TODO: Complete this test")
// //     public void testGetCategories3() {
// //         // TODO: Complete this test.
// //         //   Reason: R013 No inputs found that don't throw a trivial exception.
// //         //   Diffblue Cover tried to run the arrange/act section, but the method under
// //         //   test threw
// //         //   java.lang.IllegalArgumentException: Page index must not be less than zero
// //         //       at com.innovature.rentx.service.impl.CategoryServiceImpl.getCategories(CategoryServiceImpl.java:83)
// //         //   See https://diff.blue/R013 to resolve this issue.

// //         when(categoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any())).thenReturn(new ArrayList<>());
// //         doNothing().when(categoryUtil)
// //                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //         categoryServiceImpl.getCategories("Name", -1, 3, "Sort Field", Sort.Direction.ASC);
// //     }

//     /**
//      * Method under test: {@link CategoryServiceImpl#getCategories(String, int, int, String, Sort.Direction)}
//      */
// //     @Test
// //     public void testGetCategories4() {
// //         ArrayList<CategoryListView> categoryListViewList = new ArrayList<>();
// //         when(categoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any())).thenReturn(categoryListViewList);
// //         doNothing().when(categoryUtil)
// //                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //         List<CategoryListView> actualCategories = categoryServiceImpl.getCategories("Name", 10, 3, "Sort Field",
// //                 Sort.Direction.DESC);
// //         assertSame(categoryListViewList, actualCategories);
// //         assertTrue(actualCategories.isEmpty());
// //         verify(categoryRepository).findByNameContainingAndStatus(Mockito.<String>any(), Mockito.<Byte>any(),
// //                 Mockito.<Pageable>any());
// //         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
// //                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
// //     }
// }

