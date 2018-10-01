package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Category;
import org.apache.commons.lang3.StringUtils;

public enum CategoryValidator {
    ;

    public static void validateNewCategory(Category category){
        if(StringUtils.isEmpty(category.getName())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_CATEGORY_NAME"));
        }
        if(category.getParentCategory() != null && category.getParentCategory().getId() < 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_PARENT_ID"));
        }
    }
}
