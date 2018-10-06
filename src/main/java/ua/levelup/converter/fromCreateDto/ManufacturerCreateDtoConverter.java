package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

public class ManufacturerCreateDtoConverter
        implements Converter<ManufacturerCreateDto, Manufacturer> {

    @NotNull
    @Override
    public Manufacturer convert(@NonNull ManufacturerCreateDto manufacturerCreateDto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerCreateDto.getName());
        return manufacturer;
    }
}
