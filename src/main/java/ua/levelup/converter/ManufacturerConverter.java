package ua.levelup.converter;

import ua.levelup.model.Manufacturer;
import ua.levelup.web.dto.ManufacturerCreateDto;
import ua.levelup.web.dto.ManufacturerViewDto;

public enum ManufacturerConverter {
    ;

    public static Manufacturer asManufacturer(ManufacturerCreateDto createdManufacturer){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(createdManufacturer.getName());
        return manufacturer;
    }

    public static ManufacturerViewDto asManufacturerViewDto(Manufacturer manufacturer) {
        ManufacturerViewDto viewDto = new ManufacturerViewDto();
        viewDto.setId(manufacturer.getId());
        viewDto.setName(manufacturer.getName());
        return viewDto;
    }
}