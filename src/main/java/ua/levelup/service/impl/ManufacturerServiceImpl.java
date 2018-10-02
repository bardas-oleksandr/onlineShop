package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.model.Manufacturer;
import ua.levelup.service.ManufacturerService;
import ua.levelup.validator.ManufacturerValidator;

@Service("manufacturerService")
@Getter
@Setter
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Override
    public void createNewManufacturer(Manufacturer manufacturer) {
        ManufacturerValidator.validateNewManufacturer(manufacturer);
        manufacturerDao.add(manufacturer);
    }
}
