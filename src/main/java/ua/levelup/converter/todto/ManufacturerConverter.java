package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.view.ManufacturerViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("manufacturerConverter")
public class ManufacturerConverter implements Converter<Manufacturer, ManufacturerViewDto> {

    @Override
    public ManufacturerViewDto convert(@NonNull Manufacturer manufacturer) {
        ManufacturerViewDto manufacturerDto = new ManufacturerViewDto();
        manufacturerDto.setId(manufacturer.getId());
        manufacturerDto.setName(manufacturer.getName());
        return manufacturerDto;
    }
}