package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

/**
 *
 */
@Component("manufacturerCreateDtoConverter")
public class ManufacturerCreateDtoConverter
        implements Converter<ManufacturerCreateDto, Manufacturer> {

    @Override
    public Manufacturer convert(@NonNull ManufacturerCreateDto manufacturerCreateDto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerCreateDto.getName());
        return manufacturer;
    }
}