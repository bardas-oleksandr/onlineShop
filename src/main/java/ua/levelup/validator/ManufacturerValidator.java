package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Manufacturer;
import org.apache.commons.lang3.StringUtils;

public enum ManufacturerValidator {
    ;

    public static void validateNewManufacturer(Manufacturer manufacturer){
        if(StringUtils.isEmpty(manufacturer.getName())){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_MANUFACTURER_NAME"));
        }
    }
}
