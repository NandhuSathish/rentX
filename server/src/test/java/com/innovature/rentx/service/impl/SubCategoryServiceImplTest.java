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
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import com.innovature.rentx.entity.Category;
// import com.innovature.rentx.entity.SubCategory;
// import com.innovature.rentx.exception.ConflictException;
// import com.innovature.rentx.form.SubCategoryForm;
// import com.innovature.rentx.form.SubCategoryUpdateForm;
// import com.innovature.rentx.repository.CategoryRepository;
// import com.innovature.rentx.repository.SubCategoryRepository;
// import com.innovature.rentx.util.CategoryUtil;
// import com.innovature.rentx.util.LanguageUtil;
// import com.innovature.rentx.view.AllSubCategoryListView;
// import com.innovature.rentx.view.SubCategoryListView;
// import com.innovature.rentx.view.SubCategoryView;

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
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// @ContextConfiguration(classes = {SubCategoryServiceImpl.class})
// @RunWith(SpringJUnit4ClassRunner.class)
// public class SubCategoryServiceImplTest {
//     @MockBean
//     private CategoryRepository categoryRepository;

//     @MockBean
//     private CategoryUtil categoryUtil;

//     @MockBean
//     private LanguageUtil languageUtil;

//     @MockBean
//     private SubCategoryRepository subCategoryRepository;

//     @Autowired
//     private SubCategoryServiceImpl subCategoryServiceImpl;

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#addSubCategory(SubCategoryForm)}
//      */
//     @Test
//     public void testAddSubCategory() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

//         SubCategory subCategory = new SubCategory();
//         subCategory.setCategory(category2);
//         subCategory.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory.setId(1);
//         subCategory.setName("Name");
//         subCategory.setStatus((byte) 'A');
//         subCategory.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory);

//         SubCategoryForm subCategoryForm = new SubCategoryForm();
//         subCategoryForm.setCategoryId("42");
//         subCategoryForm.setName("Name");
//         SubCategoryView actualAddSubCategoryResult = subCategoryServiceImpl.addSubCategory(subCategoryForm);
//         assertNull(actualAddSubCategoryResult.getId());
//         assertEquals(UserServiceImpl.SUCCESSFULLY_CREATED, actualAddSubCategoryResult.getMessage());
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#addSubCategory(SubCategoryForm)}
//      */
//     @Test
//     public void testAddSubCategory2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenThrow(new ConflictException("Just cause"));

//         SubCategoryForm subCategoryForm = new SubCategoryForm();
//         subCategoryForm.setCategoryId("42");
//         subCategoryForm.setName("Name");
//         assertThrows(ConflictException.class, () -> subCategoryServiceImpl.addSubCategory(subCategoryForm));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#addSubCategory(SubCategoryForm)}
//      */
//     @Test
//     public void testAddSubCategory3() {
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory);

//         SubCategoryForm subCategoryForm = new SubCategoryForm();
//         subCategoryForm.setCategoryId("42");
//         subCategoryForm.setName("Name");
//         assertThrows(ConflictException.class, () -> subCategoryServiceImpl.addSubCategory(subCategoryForm));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#addSubCategory(SubCategoryForm)}
//      */
//     @Test
//     @Ignore("TODO: Complete this test")
//     public void testAddSubCategory4() {
//         // TODO: Complete this test.
//         //   Reason: R013 No inputs found that don't throw a trivial exception.
//         //   Diffblue Cover tried to run the arrange/act section, but the method under
//         //   test threw
//         //   java.lang.NumberFormatException: For input string: "foo"
//         //       at java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
//         //       at java.lang.Integer.parseInt(Integer.java:668)
//         //       at java.lang.Integer.parseInt(Integer.java:786)
//         //       at com.innovature.rentx.service.impl.SubCategoryServiceImpl.addSubCategory(SubCategoryServiceImpl.java:61)
//         //   See https://diff.blue/R013 to resolve this issue.

//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).categoryIdNumericCheck(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory);
//         SubCategoryForm subCategoryForm = mock(SubCategoryForm.class);
//         when(subCategoryForm.getCategoryId()).thenReturn("foo");
//         doNothing().when(subCategoryForm).setCategoryId(Mockito.<String>any());
//         doNothing().when(subCategoryForm).setName(Mockito.<String>any());
//         subCategoryForm.setCategoryId("42");
//         subCategoryForm.setName("Name");
//         subCategoryServiceImpl.addSubCategory(subCategoryForm);
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#updateSubCategory(Integer, SubCategoryUpdateForm)}
//      */
//     @Test
//     public void testUpdateSubCategory() {
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         Optional<SubCategory> ofResult = Optional.of(subCategory);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

//         SubCategory subCategory2 = new SubCategory();
//         subCategory2.setCategory(category2);
//         subCategory2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory2.setId(1);
//         subCategory2.setName("Name");
//         subCategory2.setStatus((byte) 'A');
//         subCategory2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory2);
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);

//         SubCategoryUpdateForm subCategoryUpdateForm = new SubCategoryUpdateForm();
//         subCategoryUpdateForm.setName("Name");
//         SubCategoryView actualUpdateSubCategoryResult = subCategoryServiceImpl.updateSubCategory(1,
//                 subCategoryUpdateForm);
//         assertEquals(1, actualUpdateSubCategoryResult.getId().intValue());
//         assertEquals("successfully updated", actualUpdateSubCategoryResult.getMessage());
//         verify(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#updateSubCategory(Integer, SubCategoryUpdateForm)}
//      */
//     @Test
//     public void testUpdateSubCategory2() {
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         Optional<SubCategory> ofResult = Optional.of(subCategory);
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenThrow(new ConflictException("Just cause"));
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);

//         SubCategoryUpdateForm subCategoryUpdateForm = new SubCategoryUpdateForm();
//         subCategoryUpdateForm.setName("Name");
//         assertThrows(ConflictException.class, () -> subCategoryServiceImpl.updateSubCategory(1, subCategoryUpdateForm));
//         verify(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#updateSubCategory(Integer, SubCategoryUpdateForm)}
//      */
//     @Test
//     public void testUpdateSubCategory3() {
//         doNothing().when(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         SubCategory subCategory = mock(SubCategory.class);
//         when(subCategory.getId()).thenReturn(1);
//         doNothing().when(subCategory).setCategory(Mockito.<Category>any());
//         doNothing().when(subCategory).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(subCategory).setId(Mockito.<Integer>any());
//         doNothing().when(subCategory).setName(Mockito.<String>any());
//         doNothing().when(subCategory).setStatus(Mockito.<Byte>any());
//         doNothing().when(subCategory).setUpdatedAt(Mockito.<LocalDateTime>any());
//         subCategory.setCategory(category);
//         subCategory.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory.setId(1);
//         subCategory.setName("Name");
//         subCategory.setStatus((byte) 'A');
//         subCategory.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<SubCategory> ofResult = Optional.of(subCategory);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

//         SubCategory subCategory2 = new SubCategory();
//         subCategory2.setCategory(category2);
//         subCategory2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory2.setId(1);
//         subCategory2.setName("Name");
//         subCategory2.setStatus((byte) 'A');
//         subCategory2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory2);
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);

//         SubCategoryUpdateForm subCategoryUpdateForm = new SubCategoryUpdateForm();
//         subCategoryUpdateForm.setName("Name");
//         SubCategoryView actualUpdateSubCategoryResult = subCategoryServiceImpl.updateSubCategory(1,
//                 subCategoryUpdateForm);
//         assertEquals(1, actualUpdateSubCategoryResult.getId().intValue());
//         assertEquals("successfully updated", actualUpdateSubCategoryResult.getMessage());
//         verify(categoryUtil).CheckDuplicateSubCategoryName(Mockito.<String>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//         verify(subCategory).getId();
//         verify(subCategory).setCategory(Mockito.<Category>any());
//         verify(subCategory).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(subCategory).setId(Mockito.<Integer>any());
//         verify(subCategory, atLeast(1)).setName(Mockito.<String>any());
//         verify(subCategory).setStatus(Mockito.<Byte>any());
//         verify(subCategory).setUpdatedAt(Mockito.<LocalDateTime>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#deleteSubCategory(Integer)}
//      */
//     @Test
//     public void testDeleteSubCategory() {
//         doNothing().when(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         Optional<SubCategory> ofResult = Optional.of(subCategory);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

//         SubCategory subCategory2 = new SubCategory();
//         subCategory2.setCategory(category2);
//         subCategory2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory2.setId(1);
//         subCategory2.setName("Name");
//         subCategory2.setStatus((byte) 'A');
//         subCategory2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory2);
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);
//         SubCategoryView actualDeleteSubCategoryResult = subCategoryServiceImpl.deleteSubCategory(1);
//         assertEquals(1, actualDeleteSubCategoryResult.getId().intValue());
//         assertEquals("successfully deleted", actualDeleteSubCategoryResult.getMessage());
//         verify(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#deleteSubCategory(Integer)}
//      */
//     @Test
//     public void testDeleteSubCategory2() {
//         doNothing().when(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

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
//         Optional<SubCategory> ofResult = Optional.of(subCategory);
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenThrow(new ConflictException("Just cause"));
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);
//         assertThrows(ConflictException.class, () -> subCategoryServiceImpl.deleteSubCategory(1));
//         verify(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#deleteSubCategory(Integer)}
//      */
//     @Test
//     public void testDeleteSubCategory3() {
//         doNothing().when(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         doNothing().when(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");

//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         SubCategory subCategory = mock(SubCategory.class);
//         when(subCategory.getId()).thenReturn(1);
//         doNothing().when(subCategory).setCategory(Mockito.<Category>any());
//         doNothing().when(subCategory).setCreatedAt(Mockito.<LocalDateTime>any());
//         doNothing().when(subCategory).setId(Mockito.<Integer>any());
//         doNothing().when(subCategory).setName(Mockito.<String>any());
//         doNothing().when(subCategory).setStatus(Mockito.<Byte>any());
//         doNothing().when(subCategory).setUpdatedAt(Mockito.<LocalDateTime>any());
//         subCategory.setCategory(category);
//         subCategory.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory.setId(1);
//         subCategory.setName("Name");
//         subCategory.setStatus((byte) 'A');
//         subCategory.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<SubCategory> ofResult = Optional.of(subCategory);

//         Category category2 = new Category();
//         category2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category2.setId(1);
//         category2.setName("Name");
//         category2.setStatus((byte) 'A');
//         category2.setSubCategories(new ArrayList<>());
//         category2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

//         SubCategory subCategory2 = new SubCategory();
//         subCategory2.setCategory(category2);
//         subCategory2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         subCategory2.setId(1);
//         subCategory2.setName("Name");
//         subCategory2.setStatus((byte) 'A');
//         subCategory2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         when(subCategoryRepository.save(Mockito.<SubCategory>any())).thenReturn(subCategory2);
//         when(subCategoryRepository.findByIdAndStatus(Mockito.<Integer>any(), anyByte())).thenReturn(ofResult);
//         SubCategoryView actualDeleteSubCategoryResult = subCategoryServiceImpl.deleteSubCategory(1);
//         assertEquals(1, actualDeleteSubCategoryResult.getId().intValue());
//         assertEquals("successfully deleted", actualDeleteSubCategoryResult.getMessage());
//         verify(categoryUtil).checkSubCategoryAlreadyDeleted(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(categoryUtil).requiredCheck(Mockito.<Integer>any(), Mockito.<String>any());
//         verify(subCategoryRepository).save(Mockito.<SubCategory>any());
//         verify(subCategoryRepository).findByIdAndStatus(Mockito.<Integer>any(), anyByte());
//         verify(subCategory).getId();
//         verify(subCategory).setCategory(Mockito.<Category>any());
//         verify(subCategory).setCreatedAt(Mockito.<LocalDateTime>any());
//         verify(subCategory).setId(Mockito.<Integer>any());
//         verify(subCategory).setName(Mockito.<String>any());
//         verify(subCategory, atLeast(1)).setStatus(Mockito.<Byte>any());
//         verify(subCategory).setUpdatedAt(Mockito.<LocalDateTime>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
//      */
// //     @Te


//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetSubCategoriesByCategoryId2() {
//         Category category = new Category();
//         category.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         category.setId(1);
//         category.setName("Name");
//         category.setStatus((byte) 'A');
//         category.setSubCategories(new ArrayList<>());
//         category.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
//         Optional<Category> ofResult = Optional.of(category);
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any())).thenReturn(ofResult);
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(Mockito.<Integer>any(), anyByte(),
//                 Mockito.<String>any(), Mockito.<Pageable>any())).thenThrow(new ConflictException("Just cause"));
//         assertThrows(ConflictException.class,
//                 () -> subCategoryServiceImpl.getSubCategoriesByCategoryId(1, "Search", 1, 3, "Sort", Sort.Direction.ASC));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByCategoryIdAndStatusAndNameContainingIgnoreCase(Mockito.<Integer>any(),
//                 anyByte(), Mockito.<String>any(), Mockito.<Pageable>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetSubCategoriesByCategoryId3() {
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");
//         when(subCategoryRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(Mockito.<Integer>any(), anyByte(),
//                 Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
//         assertThrows(ConflictException.class,
//                 () -> subCategoryServiceImpl.getSubCategoriesByCategoryId(1, "Search", 1, 3, "Sort", Sort.Direction.ASC));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetSubCategoriesByCategoryId4() {
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenReturn("Translated Text");
//         when(subCategoryRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(Mockito.<Integer>any(), anyByte(),
//                 Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
//         assertThrows(ConflictException.class,
//                 () -> subCategoryServiceImpl.getSubCategoriesByCategoryId(1, "Search", 1, 3, "Sort", Sort.Direction.DESC));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetSubCategoriesByCategoryId5() {
//         when(categoryRepository.findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any()))
//                 .thenReturn(Optional.empty());
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
//                 .thenThrow(new ConflictException("Just cause"));
//         when(subCategoryRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(Mockito.<Integer>any(), anyByte(),
//                 Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
//         assertThrows(ConflictException.class,
//                 () -> subCategoryServiceImpl.getSubCategoriesByCategoryId(1, "Search", 1, 3, "Sort", Sort.Direction.ASC));
//         verify(categoryRepository).findByIdAndStatus(Mockito.<Integer>any(), Mockito.<Byte>any());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(languageUtil).getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getAllSubCategories(String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetAllSubCategories() {
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         ArrayList<SubCategoryListView> subCategoryListViewList = new ArrayList<>();
//         when(subCategoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any())).thenReturn(subCategoryListViewList);
//         List<SubCategoryListView> actualAllSubCategories = subCategoryServiceImpl.getAllSubCategories("Search", 1, 3,
//                 "Sort", Sort.Direction.ASC);
//         assertSame(subCategoryListViewList, actualAllSubCategories);
//         assertTrue(actualAllSubCategories.isEmpty());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getAllSubCategories(String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetAllSubCategories2() {
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any())).thenThrow(new ConflictException("Just cause"));
//         assertThrows(ConflictException.class,
//                 () -> subCategoryServiceImpl.getAllSubCategories("Search", 1, 3, "Sort", Sort.Direction.ASC));
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any());
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getAllSubCategories(String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     @Ignore("TODO: Complete this test")
//     public void testGetAllSubCategories3() {
//         // TODO: Complete this test.
//         //   Reason: R013 No inputs found that don't throw a trivial exception.
//         //   Diffblue Cover tried to run the arrange/act section, but the method under
//         //   test threw
//         //   java.lang.IllegalArgumentException: Page index must not be less than zero
//         //       at com.innovature.rentx.service.impl.SubCategoryServiceImpl.getAllSubCategories(SubCategoryServiceImpl.java:110)
//         //   See https://diff.blue/R013 to resolve this issue.

//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         when(subCategoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any())).thenReturn(new ArrayList<>());
//         subCategoryServiceImpl.getAllSubCategories("Search", -1, 3, "Sort", Sort.Direction.ASC);
//     }

//     /**
//      * Method under test: {@link SubCategoryServiceImpl#getAllSubCategories(String, int, int, String, Sort.Direction)}
//      */
//     @Test
//     public void testGetAllSubCategories4() {
//         doNothing().when(categoryUtil)
//                 .checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                         Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         ArrayList<SubCategoryListView> subCategoryListViewList = new ArrayList<>();
//         when(subCategoryRepository.findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any())).thenReturn(subCategoryListViewList);
//         List<SubCategoryListView> actualAllSubCategories = subCategoryServiceImpl.getAllSubCategories("Search", 1, 3,
//                 "Sort", Sort.Direction.DESC);
//         assertSame(subCategoryListViewList, actualAllSubCategories);
//         assertTrue(actualAllSubCategories.isEmpty());
//         verify(categoryUtil).checkListParams(anyInt(), anyInt(), Mockito.<String>any(), Mockito.<Sort.Direction>any(),
//                 Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
//         verify(subCategoryRepository).findByNameContainingAndStatus(Mockito.<String>any(), anyByte(),
//                 Mockito.<Pageable>any());
//     }
// }

