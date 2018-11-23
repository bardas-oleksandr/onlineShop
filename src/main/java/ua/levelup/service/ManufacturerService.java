package ua.levelup.service;

import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import java.util.List;

public interface ManufacturerService {
    ManufacturerViewDto create(ManufacturerCreateDto manufacturerCreateDto);

    ManufacturerViewDto update(ManufacturerCreateDto manufacturerCreateDto
            , int manufacturerId);

    void delete(int manufacturerId);

    ManufacturerViewDto get(int manufacturerId);

    List<ManufacturerViewDto> getAll();
}