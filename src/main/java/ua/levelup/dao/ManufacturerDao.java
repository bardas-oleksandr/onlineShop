package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Manufacturer;

import java.util.List;

public interface ManufacturerDao {

    Manufacturer add(Manufacturer manufacturer) throws ApplicationException;

    int update(Manufacturer manufacturer) throws ApplicationException;

    int delete(int id) throws ApplicationException;

    Manufacturer getById(int id) throws ApplicationException;

    List<Manufacturer> getAllByCategoryId(int categoryId) throws ApplicationException;

    List<Manufacturer> getAll() throws ApplicationException;
}
