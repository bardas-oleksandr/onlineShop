package ua.levelup.service.impl;

import ua.levelup.converter.ManufacturerConverter;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.model.Manufacturer;
import ua.levelup.service.ManufacturerService;
import ua.levelup.validator.ManufacturerValidator;
import ua.levelup.web.dto.ManufacturerCreateDto;

public class ManufacturerServiceImpl implements ManufacturerService {

    private ManufacturerDao manufacturerDao = (ManufacturerDao) DaoHolder
            .getDaoObject(DaoHolder.MANUFACTURER_DAO);

    @Override
    public void createNewManufacturer(ManufacturerCreateDto manufacturerCreateDto) {
        ManufacturerValidator.validateNewManufacturer(manufacturerCreateDto);
        Manufacturer manufacturer = ManufacturerConverter.asManufacturer(manufacturerCreateDto);
        manufacturerDao.add(manufacturer);
    }
}
