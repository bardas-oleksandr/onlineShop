package ua.levelup.service;

import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import java.util.List;

public interface ManufacturerService {
    ManufacturerViewDto createManufacturer(ManufacturerCreateDto manufacturerCreateDto);
    List<ManufacturerViewDto> getAllManufacturers();
}