package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.web.dto.ManufacturerCreateDto;
import org.apache.commons.lang3.StringUtils;

public enum ManufacturerValidator {
    ;

    public static void validateNewManufacturer(ManufacturerCreateDto manufacturerCreateDto){
        if(StringUtils.isEmpty(manufacturerCreateDto.getName())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_MANUFACTURER_NAME"));
        }
    }
}
