package ua.levelup.converter;

import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.ManufacturerDto;

public enum ManufacturerConverter {
    ;

    public static ManufacturerDto asManufacturerDto(Manufacturer manufacturer) {
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(manufacturer.getId());
        manufacturerDto.setName(manufacturer.getName());
        return manufacturerDto;
    }
}