package ua.levelup.service;

import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import javax.validation.Valid;
import java.util.List;

public interface ManufacturerService {
    void createNewManufacturer(@Valid ManufacturerCreateDto manufacturerCreateDto);
    List<ManufacturerViewDto> getAllManufacturers();
}
