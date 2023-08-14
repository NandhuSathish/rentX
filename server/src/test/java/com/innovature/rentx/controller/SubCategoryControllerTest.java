package com.innovature.rentx.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.SubCategoryForm;
import com.innovature.rentx.form.SubCategoryUpdateForm;
import com.innovature.rentx.service.SubCategoryService;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.view.SubCategoryView;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SubCategoryController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SubCategoryControllerTest {
    @MockBean
    private LanguageUtil languageUtil;

    @Autowired
    private SubCategoryController subCategoryController;

    @MockBean
    private SubCategoryService subCategoryService;

    /**
     * Method under test: {@link SubCategoryController#updateSubCategory(Integer, SubCategoryUpdateForm)}
     */
    @Test
    public void testUpdateSubCategory() throws Exception {
        when(subCategoryService.updateSubCategory(Mockito.<Integer>any(), Mockito.<SubCategoryUpdateForm>any()))
                .thenReturn(new SubCategoryView());

        SubCategoryUpdateForm subCategoryUpdateForm = new SubCategoryUpdateForm();
        subCategoryUpdateForm.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(subCategoryUpdateForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/{subCategoryId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"message\":null}"));
    }

    /**
     * Method under test: {@link SubCategoryController#updateSubCategory(Integer, SubCategoryUpdateForm)}
     */
    @Test
    public void testUpdateSubCategory2() throws Exception {
        when(subCategoryService.updateSubCategory(Mockito.<Integer>any(), Mockito.<SubCategoryUpdateForm>any()))
                .thenThrow(new BadRequestException());

        SubCategoryUpdateForm subCategoryUpdateForm = new SubCategoryUpdateForm();
        subCategoryUpdateForm.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(subCategoryUpdateForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/{subCategoryId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#addSubCategory(SubCategoryForm)}
     */
    @Test
    public void testAddSubCategory() throws Exception {
        SubCategoryForm subCategoryForm = new SubCategoryForm();
        subCategoryForm.setCategoryId("42");
        subCategoryForm.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(subCategoryForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subcategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategory(Integer)}
     */
    @Test
    public void testDeleteSubCategory() throws Exception {
        when(subCategoryService.deleteSubCategory(Mockito.<Integer>any())).thenReturn(new SubCategoryView());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/{subCategoryId}",
                1);
        MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"message\":null}"));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategory(Integer)}
     */
    @Test
    public void testDeleteSubCategory2() throws Exception {
        when(subCategoryService.deleteSubCategory(Mockito.<Integer>any())).thenThrow(new BadRequestException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/{subCategoryId}",
                1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategory(Integer)}
     */
    @Test
    public void testDeleteSubCategory3() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenReturn("Translated Text");
        when(subCategoryService.deleteSubCategory(Mockito.<Integer>any())).thenReturn(new SubCategoryView());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/{subCategoryId}",
                "", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategory(Integer)}
     */
    @Test
    public void testDeleteSubCategory4() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenThrow(new BadRequestException());
        when(subCategoryService.deleteSubCategory(Mockito.<Integer>any())).thenReturn(new SubCategoryView());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/{subCategoryId}",
                "", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategoryRequired(String)}
     */
    @Test
    public void testDeleteSubCategoryRequired() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenReturn("Translated Text");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategoryRequired(String)}
     */
    @Test
    public void testDeleteSubCategoryRequired2() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenReturn("Translated Text");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/")
                .param("category", "foo");
        MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategoryRequired(String)}
     */
    @Test
    public void testDeleteSubCategoryRequired3() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenThrow(new BadRequestException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#deleteSubCategoryRequired(String)}
     */
    @Test
    public void testDeleteSubCategoryRequired4() throws Exception {
        when(languageUtil.getTranslatedText(Mockito.<String>any(), Mockito.<Object[]>any(), Mockito.<String>any()))
                .thenReturn("Translated Text");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/subcategory/delete/")
                .param("category", "");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subCategoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SubCategoryController#getAllSubCategories(Integer, String, int, int, String, Sort.Direction)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetAllSubCategories() throws Exception {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/subcategory/", uriVariables);
        String[] values = new String[]{String.valueOf(Sort.Direction.ASC)};
        MockHttpServletRequestBuilder paramResult = getResult.param("direction", values);
        String[] values2 = new String[]{String.valueOf(1)};
        String[] values3 = new String[]{"foo"};
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("page", values2).param("search", values3);
        String[] values4 = new String[]{String.valueOf(1)};
        String[] values5 = new String[]{"foo"};
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("size", values4).param("sort", values5);
        Object[] controllers = new Object[]{subCategoryController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link SubCategoryController#getSubCategoriesByCategoryId(Integer, String, int, int, String, Sort.Direction)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetSubCategoriesByCategoryId() throws Exception {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        Object[] uriVariables = new Object[]{1};
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/subcategory/{categoryId}", uriVariables);
        String[] values = new String[]{String.valueOf(Sort.Direction.ASC)};
        MockHttpServletRequestBuilder paramResult = getResult.param("direction", values);
        String[] values2 = new String[]{String.valueOf(1)};
        String[] values3 = new String[]{"foo"};
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("page", values2).param("search", values3);
        String[] values4 = new String[]{String.valueOf(1)};
        String[] values5 = new String[]{"foo"};
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("size", values4).param("sort", values5);
        Object[] controllers = new Object[]{subCategoryController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }
}

