package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.model.Manufacturer;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import java.util.ArrayList;
import java.util.List;

@Service("manufacturerService")
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ManufacturerViewDto createManufacturer(ManufacturerCreateDto manufacturerCreateDto) {
        Manufacturer manufacturer = conversionService.convert(manufacturerCreateDto, Manufacturer.class);
        manufacturerDao.add(manufacturer);
        return conversionService.convert(manufacturer, ManufacturerViewDto.class);
    }

    @Override
    public List<ManufacturerViewDto> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        List<ManufacturerViewDto> viewDtos = new ArrayList<>();
        manufacturers.stream().forEach((item) -> viewDtos
                .add(conversionService.convert(item, ManufacturerViewDto.class)));
        return viewDtos;
    }
}