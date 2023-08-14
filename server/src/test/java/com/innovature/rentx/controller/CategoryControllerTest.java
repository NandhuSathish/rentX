package com.innovature.rentx.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovature.rentx.form.CategoryForm;
import com.innovature.rentx.service.CategoryService;
import com.innovature.rentx.view.CategoryView;

import java.util.ArrayList;

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

@ContextConfiguration(classes = {CategoryController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    /**
     * Method under test: {@link CategoryController#deleteCategory(Integer)}
     */
    @Test
    public void testDeleteCategory3() throws Exception {
        when(categoryService.deleteCategory(Mockito.<Integer>any())).thenReturn(new CategoryView());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/delete/{categoryId}", 1);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"message\":null}"));
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory(Integer)}
     */
    @Test
    public void testDeleteCategory4() throws Exception {
        when(categoryService.deleteCategory(Mockito.<Integer>any())).thenReturn(new CategoryView());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/delete/{categoryId}", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }

    /**
     * Method under test: {@link CategoryController#getCategories(String, int, int, String, Sort.Direction)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetCategories() throws Exception {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category", uriVariables);
        String[] values = new String[]{String.valueOf(Sort.Direction.ASC)};
        MockHttpServletRequestBuilder paramResult = getResult.param("direction", values);
        String[] values2 = new String[]{String.valueOf(1)};
        String[] values3 = new String[]{"foo"};
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("page", values2).param("search", values3);
        String[] values4 = new String[]{String.valueOf(1)};
        String[] values5 = new String[]{"foo"};
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("size", values4).param("sort", values5);
        Object[] controllers = new Object[]{categoryController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link CategoryController#updateCategory(Integer, CategoryForm)}
     */
    @Test
    public void testUpdateCategory() throws Exception {
        when(categoryService.updateCategory(Mockito.<Integer>any(), Mockito.<CategoryForm>any()))
                .thenReturn(new CategoryView());

        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(categoryForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/{categoryId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"message\":null}"));
    }

    /**
     * Method under test: {@link CategoryController#updateCategory(Integer, CategoryForm)}
     */
    @Test
    public void testUpdateCategory2() throws Exception {
        when(categoryService.updateCategory(Mockito.<Integer>any(), Mockito.<CategoryForm>any()))
                .thenReturn(new CategoryView());

        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(categoryForm);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category/{categoryId}", "", "Uri Variables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory()}
     */
    @Test
    public void testDeleteCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/delete/");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory()}
     */
    @Test
    public void testDeleteCategory2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/delete/", "Uri Variables");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }


    /**
     * Method under test: {@link CategoryController#registerCategory(CategoryForm)}
     */
//     @Test
//     public void testRegisterCategory() throws Exception {
//         when(categoryService.getCategories(Mockito.<String>any(), anyInt(), anyInt(), Mockito.<String>any(),
//                 Mockito.<Sort.Direction>any())).thenReturn(new ArrayList<>());

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         String content = (new ObjectMapper()).writeValueAsString(categoryForm);
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(content);
//         MockMvcBuilders.standaloneSetup(categoryController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                 .andExpect(MockMvcResultMatchers.content().string("[]"));
//     }

    /**
     * Method under test: {@link CategoryController#registerCategory(CategoryForm)}
     */
//     @Test
//     public void testRegisterCategory2() throws Exception {
//         when(categoryService.getCategories(Mockito.<String>any(), anyInt(), anyInt(), Mockito.<String>any(),
//                 Mockito.<Sort.Direction>any())).thenReturn(new ArrayList<>());
//         MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category");
//         getResult.characterEncoding("Encoding");

//         CategoryForm categoryForm = new CategoryForm();
//         categoryForm.setName("Name");
//         String content = (new ObjectMapper()).writeValueAsString(categoryForm);
//         MockHttpServletRequestBuilder requestBuilder = getResult.contentType(MediaType.APPLICATION_JSON).content(content);
//         MockMvcBuilders.standaloneSetup(categoryController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                 .andExpect(MockMvcResultMatchers.content().string("[]"));
//     }

    /**
     * Method under test: {@link CategoryController#registerCategory()}
     */
    @Test
    public void testRegisterCategory3() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }

    /**
     * Method under test: {@link CategoryController#registerCategory()}
     */
    @Test
    public void testRegisterCategory4() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"errorCode\":\"3012\",\"errorMessage\":\"Category id is required\"}"));
    }
}

