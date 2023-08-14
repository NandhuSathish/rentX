package com.innovature.rentx.service.impl;

import javax.transaction.Transactional;

import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.ImageProduct;
import com.innovature.rentx.entity.Product;
import com.innovature.rentx.entity.Store;
import com.innovature.rentx.entity.SubCategory;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.ImageProductForm;
import com.innovature.rentx.form.ProductForm;
import com.innovature.rentx.repository.CategoryRepository;
import com.innovature.rentx.repository.ImageProductRepository;
import com.innovature.rentx.repository.ProductRepository;
import com.innovature.rentx.repository.StoreRepository;
import com.innovature.rentx.repository.SubCategoryRepository;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.security.util.SecurityUtil;
import com.innovature.rentx.service.ProductService;
import com.innovature.rentx.util.LanguageUtil;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ImageProductRepository imageProductRepository;

    @Autowired
    LanguageUtil languageUtil;

    private static final String UNABLE_TO_PERFORM = "unable.to.perform.this.action";

    private static final String INVALID_PRODUCT_ID = "invalid.product.id";


    private static final String SEPARATOR = ",";

    private final EntityManager entityManager;

    private byte[] productAcceptedStatus;

    private byte[] imageProductAcceptedStatus;

    public ProductServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProductView createProduct(ProductForm form) {

        User user = userRepository.findByIdAndStatus(SecurityUtil.getCurrentUserId(), Store.Status.ACTIVE.value);
        if (user.getStatus() != User.Status.ACTIVE.value) {
            throw new BadRequestException(languageUtil.getTranslatedText("user.blocked", null, "en"));
        } else {

            Category category = categoryRepository.findByIdAndStatus(form.getCategory(), Category.STATUS.ACTIVE.value)
                    .orElseThrow(() -> new BadRequestException(
                            languageUtil.getTranslatedText("invalid.category.id", null, "en")));

            SubCategory subCategory = subCategoryRepository
                    .findByIdAndCategoryIdAndStatus(form.getSubCategory(),form.getCategory(), SubCategory.STATUS.ACTIVE.value)
                    .orElseThrow(() -> new BadRequestException(
                            languageUtil.getTranslatedText("subCategoryId.should.required", null, "en")));

            Store store = storeRepository
                    .findByIdAndUserIdAndStatus(form.getStore(), SecurityUtil.getCurrentUserId(),
                            Store.Status.ACTIVE.value)
                    .orElseThrow(
                            () -> new BadRequestException(
                                    languageUtil.getTranslatedText("invalid.store.id", null, "en")));

            if (productRepository.existsByNameAndStoreId(form.getName(), form.getStore())) {
                throw new BadRequestException(
                        languageUtil.getTranslatedText("Product-Name.already.exists", null, "en"));
            }
     
            else {
                return new ProductView(productRepository.save(new Product(form, category, subCategory, store)));

            }

        }
    }

    @Transactional
    public ResponseEntity<String> addImageProduct(ImageProductForm form) {


        Product product = productRepository.findById(form.getProductId());
        if(product != null){

            switch(product.getStatus()){
                case 0->{
                     throw new BadRequestException(
                        languageUtil.getTranslatedText("image.exists.already", null, "en"));
                }
                case 1->{
                    imageProductRepository.save(new ImageProduct(form));
                    product.setStatus(Product.Status.ACTIVE.value);
                }
                case 2->{
                    throw new BadRequestException(
                        languageUtil.getTranslatedText(INVALID_PRODUCT_ID, null, "en"));
                }
            default -> throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

        }
        else{
            throw new BadRequestException(
                languageUtil.getTranslatedText(INVALID_PRODUCT_ID, null, "en"));
        }

        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Integer productId) {

        try {
            Product product = productRepository.findById(productId);
            product.setStatus(Product.Status.DELETED.value);

            ImageProduct image = imageProductRepository.findByProductId(productId);
            image.setStatus(ImageProduct.Status.DELETED.value);

            productRepository.save(product);
            imageProductRepository.save(image);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("invalid.product.id", null, "en"));
        }

    }

    @Override
    public Pager<UserProductView> viewProduct(String searchData, Integer page, Integer size, String sort, String order,
            String CategoryId, String StoreId) {

        Boolean isCarted=Boolean.TRUE;
        Boolean isWishlisted=Boolean.TRUE;
        Integer isAvailable=10;

        boolean orderD = !order.equalsIgnoreCase("asc");
        Pager<UserProductView> viewProduct;
        List<UserProductView> productViewList;

        try {
            page = Integer.parseInt(String.valueOf(page));
            size = Integer.parseInt(String.valueOf(size));

        } catch (NumberFormatException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

        if (page < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Page.natual", null, "en"));
        }

        if (size < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Size.natural", null, "en"));
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQueryUser = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQueryUser.from(Product.class);
        List<Predicate> predicate = new ArrayList<>();
        Join<Product, Category> categoryJoin = productRoot.join("category", JoinType.INNER);
        Join<Product, Store> storeJoin = productRoot.join("store", JoinType.INNER);
        Join<Store, User> userJoin = storeJoin.join("user", JoinType.INNER);


        Predicate predicateUserRole = criteriaBuilder.equal(userJoin.get("role"), User.Role.VENDOR.value);
        Predicate predicateUserStatus = criteriaBuilder.equal(userJoin.get("status"), User.Status.ACTIVE.value);
        Predicate predicateProductStatus=criteriaBuilder.equal(productRoot.get("status"),Product.Status.ACTIVE.value);

        predicate.add(predicateUserStatus);
        predicate.add(predicateUserRole);
        predicate.add(predicateProductStatus);

        if (!StringUtils.isEmpty(CategoryId)) {
            String[] categoryId = CategoryId.split(SEPARATOR);
            Predicate predicateCategory = categoryJoin.get("id").in(categoryId);
            predicate.add(predicateCategory);
        }
        if (!StringUtils.isEmpty(StoreId)) {

            String[] storeId = StoreId.split(SEPARATOR);
            Predicate predicateStore = storeJoin.get("id").in(storeId);
            predicate.add(predicateStore);
        }
        if (!StringUtils.isEmpty(searchData)) {
            searchData = searchData.trim();
            Predicate predicateProductName = criteriaBuilder.like(criteriaBuilder.lower(productRoot.get("name")),
                    "%" + searchData + "%");
            Predicate predicateCategoryName = criteriaBuilder
                    .like(criteriaBuilder.lower(productRoot.get("category").get("name")), "%" + searchData + "%");
            Predicate predicateStoreName = criteriaBuilder
                    .like(criteriaBuilder.lower(productRoot.get("store").get("name")), "%" + searchData + "%");
            Predicate predicateSearch = criteriaBuilder.or(predicateProductName, predicateCategoryName,
                    predicateStoreName);

            predicate.add(predicateSearch);
        }

        Predicate whereCondition = criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
        criteriaQueryUser.where(whereCondition);

        if (sort != null) {

            if (order.equalsIgnoreCase("asc")) {
                criteriaQueryUser.orderBy(criteriaBuilder.asc(productRoot.get(sort)));
            } else {
                criteriaQueryUser.orderBy(criteriaBuilder.desc(productRoot.get(sort)));
            }
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText("item.invalid.sort", null, "en"));
        }
        TypedQuery<Product> list = entityManager.createQuery(criteriaQueryUser);
        list.setFirstResult((page - 1) * size);
        list.setMaxResults(size);
        Long count;
        // count=
        // Long.valueOf(entityManager.createQuery(criteriaQueryUser).getResultList().size());
        count = (long) entityManager.createQuery(criteriaQueryUser).getResultList().size();
        Page<Product> result = new PageImpl<>(list.getResultList(),
                PageRequest.of(page - 1, size, (orderD == true) ? Sort.Direction.DESC : Sort.Direction.ASC, sort),
                count);

        productViewList = result.stream()
                .map(product -> new UserProductView(product, isAvailable, isCarted, isWishlisted))
                .collect(Collectors.toList());
//                productViewList = result.stream()
//                .map(product -> {
//                            List<ImageProduct> image= StreamSupport.stream(imageProductRepository
//                                            .findByProductIdAndStatus(product.getId(),
//                                    ImageProduct.Status.ACTIVE.value)
//                                    .spliterator(),false)
//                    .collect(Collectors.toList());
//                            return new UserProductDetailView(product,isAvailable,isCarted,isWishlisted,image);
//                        }
//                )
//                .collect(Collectors.toList());


        viewProduct = new Pager<>(size, count.intValue(), page);
        viewProduct.setResult(productViewList);
        return viewProduct;

    }



    public ProductDetailView detailView(Integer productId) {

        try {

            productAcceptedStatus = new byte[] { Product.Status.ACTIVE.value, Product.Status.INACTIVE.value };

            Product products = productRepository.findByIdAndStatusIn(productId, productAcceptedStatus);

            imageProductAcceptedStatus = new byte[] { ImageProduct.Status.ACTIVE.value,
                    ImageProduct.Status.INACTIVE.value };

            ImageProduct image = imageProductRepository.findByProductIdAndStatusIn(productId,
                    imageProductAcceptedStatus);

            return new ProductDetailView(products, image);

        } catch (NullPointerException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("invalid.product.id", null, "en"), e);

        }

    }

    @Override
    public Pager<UserProductView> vendorViewProduct(String searchData, Integer page, Integer size, String sort, String order,
                                              String CategoryId, String StoreId) {


        Boolean isCarted=Boolean.TRUE;
        Boolean isWishlisted=Boolean.TRUE;
        Integer isAvailable=10;


        boolean orderD = !order.equalsIgnoreCase("asc");
        Pager<UserProductView> viewProduct;
        List<UserProductView> productViewList;

        try {
            page = Integer.parseInt(String.valueOf(page));
            size = Integer.parseInt(String.valueOf(size));

        } catch (NumberFormatException e) {
            throw new BadRequestException(languageUtil.getTranslatedText(UNABLE_TO_PERFORM, null, "en"));
        }

        if (page < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Page.natual", null, "en"));
        }

        if (size < 1) {
            throw new BadRequestException(languageUtil.getTranslatedText("Size.natural", null, "en"));
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQueryUser = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQueryUser.from(Product.class);
        List<Predicate> predicate = new ArrayList<>();
        Join<Product, Category> categoryJoin = productRoot.join("category", JoinType.INNER);
        Join<Product, Store> storeJoin = productRoot.join("store", JoinType.INNER);
        Join<Store, User> userJoin = storeJoin.join("user", JoinType.INNER);

        Predicate predicateUserId=criteriaBuilder.equal(userJoin.get("id"),SecurityUtil.getCurrentUserId());


        Predicate predicateUserRole = criteriaBuilder.equal(userJoin.get("role"), User.Role.VENDOR.value);
        Predicate predicateUserStatus = criteriaBuilder.equal(userJoin.get("status"), User.Status.ACTIVE.value);
        Predicate predicateProductStatus=criteriaBuilder.equal(productRoot.get("status"),Product.Status.ACTIVE.value);



        predicate.add(predicateUserId);
        predicate.add(predicateUserStatus);
        predicate.add(predicateUserRole);
        predicate.add(predicateProductStatus);





        if (!StringUtils.isEmpty(CategoryId)) {
            String[] categoryId = CategoryId.split(SEPARATOR);
            Predicate predicateCategory = categoryJoin.get("id").in(categoryId);
            predicate.add(predicateCategory);
        }
        if (!StringUtils.isEmpty(StoreId)) {

            String[] storeId = StoreId.split(SEPARATOR);
            Predicate predicateStore = storeJoin.get("id").in(storeId);
            predicate.add(predicateStore);
        }

        if (!StringUtils.isEmpty(searchData)) {

            searchData = searchData.trim();
            Predicate predicateProductName = criteriaBuilder.like(criteriaBuilder.lower(productRoot.get("name")),
                    "%" + searchData + "%");
            Predicate predicateCategoryName = criteriaBuilder
                    .like(criteriaBuilder.lower(productRoot.get("category").get("name")), "%" + searchData + "%");
            Predicate predicateStoreName = criteriaBuilder
                    .like(criteriaBuilder.lower(productRoot.get("store").get("name")), "%" + searchData + "%");
            Predicate predicateSearch = criteriaBuilder.or(predicateProductName, predicateCategoryName,
                    predicateStoreName);

            predicate.add(predicateSearch);
        }

        Predicate whereCondition = criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
        criteriaQueryUser.where(whereCondition);

        if (sort != null) {

            if (order.equalsIgnoreCase("asc")) {
                criteriaQueryUser.orderBy(criteriaBuilder.asc(productRoot.get(sort)));
            } else {
                criteriaQueryUser.orderBy(criteriaBuilder.desc(productRoot.get(sort)));
            }
        } else {
            throw new BadRequestException(languageUtil.getTranslatedText("item.invalid.sort", null, "en"));
        }
        TypedQuery<Product> list = entityManager.createQuery(criteriaQueryUser);
        list.setFirstResult((page - 1) * size);
        list.setMaxResults(size);
        Long count;
        // Long.valueOf(entityManager.createQuery(criteriaQueryUser).getResultList().size());
         count = (long) entityManager.createQuery(criteriaQueryUser).getResultList().size();
        Page<Product> result = new PageImpl<>(list.getResultList(),
                PageRequest.of(page - 1, size, (orderD == true) ? Sort.Direction.DESC : Sort.Direction.ASC, sort),
                count);
//
//        productViewList = result.stream()
//                .map(UserProductView::new)
//                .collect(Collectors.toList());

        productViewList = result.stream()
                .map(product -> new UserProductView(product, isAvailable, isCarted, isWishlisted))
                .collect(Collectors.toList());

        viewProduct = new Pager<>(size, count.intValue(), page);
        viewProduct.setResult(productViewList);
        return viewProduct;

    }


    @Override
    public UserProductDetailView userProductDetailView(Integer productId){
        Boolean isCarted=Boolean.TRUE;
        Boolean isWishlisted=Boolean.TRUE;
        Integer isAvailable=10;

        try {

            productAcceptedStatus = new byte[] { Product.Status.ACTIVE.value};

            Product products = productRepository.findByIdAndStatusIn(productId, productAcceptedStatus);

            imageProductAcceptedStatus = new byte[] { ImageProduct.Status.ACTIVE.value};

            ArrayList<ImageProduct> image = imageProductRepository.findByProductIdAndStatus(productId,
                    ImageProduct.Status.ACTIVE.value);

            List<UserProductImageView>imageView=new ArrayList<>();
//            for(ImageProduct imageProduct:image){
//                UserProductImageView userProductImageView=new UserProductImageView();
//                userProductImageView.setImage1(imageProduct.getImage1());
//                imageView.add(userProductImageView);
//            }
//            for(ImageProduct imageProduct:image){
//                UserProductImageView userProductImageView=new UserProductImageView();
//                userProductImageView.setImage2(imageProduct.getImage2());
//                imageView.add(userProductImageView);
//            }
//            for(ImageProduct imageProduct:image){
//                UserProductImageView userProductImageView=new UserProductImageView();
//                userProductImageView.setImage3(imageProduct.getImage3());
//                imageView.add(userProductImageView);
//            }
//            for(ImageProduct imageProduct:image){
//                UserProductImageView userProductImageView=new UserProductImageView();
//                userProductImageView.setImage4(imageProduct.getImage4());
//                imageView.add(userProductImageView);
//            }


            UserProductImageView userProductImageView=new UserProductImageView();



            for(ImageProduct imageProduct:image){
                userProductImageView.setImage1(products.getCoverImage());
                userProductImageView.setImage2(imageProduct.getImage1());
                userProductImageView.setImage3(imageProduct.getImage2());
                userProductImageView.setImage4(imageProduct.getImage3());
                userProductImageView.setImage5(imageProduct.getImage4());
                imageView.add(userProductImageView);
            }
            return new UserProductDetailView(products,isAvailable,isCarted,isWishlisted,imageView);


        } catch (NullPointerException e) {
            throw new BadRequestException(languageUtil.getTranslatedText("invalid.product.id", null, "en"), e);

        }
    }

}
