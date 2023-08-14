//package com.innovature.rentx.service.impl;
//
//import com.innovature.rentx.entity.Category;
//import com.innovature.rentx.entity.Product;
//import com.innovature.rentx.entity.Store;
//import com.innovature.rentx.entity.User;
//import com.innovature.rentx.util.Pager;
//import com.innovature.rentx.view.UserProductView;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.JoinType;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ProductServiceImplTest {
//
//    @Mock
//    private EntityManager entityManager;
//
//    @Mock
//    private CriteriaBuilder criteriaBuilder;
//
//    @Mock
//    private CriteriaQuery<Product> criteriaQuery;
//
//    @Mock
//    private Root<Product> productRoot;
//
//    @Mock
//    private Join<Product, Category> categoryJoin;
//
//    @Mock
//    private Join<Product, Store> storeJoin;
//
//    @Mock
//    private Join<Store, User> userJoin;
//
//    @Mock
//    private TypedQuery<Product> typedQuery;
//
//    @InjectMocks
//    private ProductServiceImpl ProductServiceImpl; // Replace with the actual class name
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void viewProduct() {
//        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
//        when(criteriaBuilder.createQuery(Product.class)).thenReturn(criteriaQuery);
//        when(criteriaQuery.from(Product.class)).thenReturn(productRoot);
////        when(productRoot.join("category", JoinType.INNER)).thenReturn(categoryJoin);
//        when(productRoot.join("category", JoinType.INNER)).thenReturn(Mockito.<Join<?, ?>>mock(Join.class));
//
////        when(productRoot.join("store", JoinType.INNER)).thenReturn(storeJoin);
////        when(storeJoin.join("user", JoinType.INNER)).thenReturn(userJoin);
//        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
//        when(typedQuery.getResultList()).thenReturn(new ArrayList<>());
//        when(entityManager.createQuery(criteriaQuery).getResultList()).thenReturn(new ArrayList<>());
//
//        // Set up test inputs
//        String searchData = "search term";
//        Integer page = 1;
//        Integer size = 10;
//        String sort = "name";
//        String order = "asc";
//        String CategoryId = "1,2,3";
//        String StoreId = "4,5,6";
//
//        // Call the method
//        Pager<UserProductView> result = ProductServiceImpl.viewProduct(searchData, page, size, sort, order, CategoryId, StoreId);
//
//        // Assertions or verifications
//        Assertions.assertNotNull(result);
//        // Add more assertions as needed
//    }
//
//}