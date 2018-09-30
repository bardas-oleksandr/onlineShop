package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.web.dto.CategoryCreateDto;
import org.apache.commons.lang3.StringUtils;

public enum CategoryValidator {
    ;

    public static void validateNewCategory(CategoryCreateDto categoryCreateDto){
        if(StringUtils.isEmpty(categoryCreateDto.getName())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_CATEGORY_NAME"));
        }
        if(categoryCreateDto.getParentId() < 0){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("UNACCEPTABLE_PARENT_ID"));
        }
    }
}
