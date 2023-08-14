package com.innovature.rentx.util;
import com.innovature.rentx.entity.Category.STATUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.exception.ConflictException;
import com.innovature.rentx.repository.CategoryRepository;
import com.innovature.rentx.repository.SubCategoryRepository;
@Component
public class CategoryUtil {
    @Autowired
    private LanguageUtil languageUtil;
    @Autowired
    private CategoryRepository categoryRepository; 
     @Autowired
    private SubCategoryRepository subCategoryRepository;   
    public void requiredCheck(Integer id, String message) {
        if ( id == 0 || id==null) {
            throw new BadRequestException(languageUtil.getTranslatedText(message, null, "en"));
        }

        
    }
    
    public void categoryIdNumericCheck(String id, String message) {
        if (!id.matches("\\d+")) {
            throw new BadRequestException(languageUtil.getTranslatedText(message, null, "en"));
        }
    }
    public void checkDuplicateCategoryName(String name, String message) {
        if (categoryRepository.findByNameAndStatus(name,STATUS.ACTIVE.value).isPresent()) {
            throw new ConflictException(languageUtil.getTranslatedText(message, null, "en"));
        }
    }
    public void checkCategoryAlreadyDeleted(Integer id, String message) {
        if (categoryRepository.existsByIdAndStatus(id, STATUS.DELETED.value)) {
            throw new ConflictException(languageUtil.getTranslatedText(message, null, "en"));
        }
    }

    public void checkListParams(int pageNumber, int pageSize, String sortField, Direction sortDirection,
    String invalidPageNumber, String invalidPageSize, String invalidSortValue, String invalidSortDirection) {
    try {
        Integer.parseInt(String.valueOf(pageNumber));
    } catch (NumberFormatException ex) {
        throw new BadRequestException(languageUtil.getTranslatedText(invalidPageNumber, null, "en"));
    }
if (pageNumber <= 0 ) {
throw new BadRequestException(languageUtil.getTranslatedText(invalidPageNumber, null, "en"));
}
try {
    Integer.parseInt(String.valueOf(pageSize));
} catch (NumberFormatException ex) {
    throw new BadRequestException(languageUtil.getTranslatedText(invalidPageSize, null, "en"));
}
if (pageSize < 0) {
throw new BadRequestException(languageUtil.getTranslatedText(invalidPageSize, null, "en"));
}


if (sortField.contains(" ")) {
    throw new BadRequestException(languageUtil.getTranslatedText(invalidSortValue, null, "en"));
}
if (sortDirection == null || sortDirection.toString().trim().isEmpty()) {
    throw new BadRequestException(languageUtil.getTranslatedText(invalidSortDirection, null, "en"));
}

if(!sortDirection.equals(Sort.Direction.ASC) && !sortDirection.equals(Sort.Direction.DESC))
{
    throw new BadRequestException(languageUtil.getTranslatedText(invalidSortDirection, null, "en"));
}
}

    public void CheckDuplicateSubCategoryName(String name, String message) {
        if (subCategoryRepository.findByNameAndStatus(name,STATUS.ACTIVE.value).isPresent()) {
            throw new ConflictException(languageUtil.getTranslatedText(message, null, "en"));
        }
    }

    public void checkSubCategoryAlreadyDeleted(Integer id, String message) {
        if (subCategoryRepository.existsByIdAndStatus(id, STATUS.DELETED.value)) {
            throw new ConflictException(languageUtil.getTranslatedText(message, null, "en"));
        }
    }
}

