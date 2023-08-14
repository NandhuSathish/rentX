package com.innovature.rentx.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.innovature.rentx.entity.Category;
import com.innovature.rentx.view.CategoryListView;
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Optional<Category> findByIdAndStatus(Integer id, Byte status);
    Optional<Category> findByName(String name);
    Optional<Category> findById(Integer id);
    Page<Category> findByNameContainingAndStatus(String name, Byte status, Pageable pageable);
    Optional<Category> findByNameAndStatus(String name, byte value);
    boolean existsByIdAndStatus(Integer id, byte value);
    Page<Category> findByNameContainingAndStatus(String name, byte value, Pageable pageable);
    int countByStatus(byte value);
    int countByNameContainingAndStatus(String name, byte value);
    Iterable<Category> findAllByStatus(byte value);
    
}
