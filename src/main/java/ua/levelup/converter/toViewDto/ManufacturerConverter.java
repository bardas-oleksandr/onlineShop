package ua.levelup.converter.toViewDto;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.view.ManufacturerViewDto;

@Component("manufacturerConverter")
public class ManufacturerConverter implements Converter<Manufacturer, ManufacturerViewDto> {

    @NotNull
    @Override
    public ManufacturerViewDto convert(@NonNull Manufacturer manufacturer) {
        ManufacturerViewDto manufacturerDto = new ManufacturerViewDto();
        manufacturerDto.setId(manufacturer.getId());
        manufacturerDto.setName(manufacturer.getName());
        return manufacturerDto;
    }
}