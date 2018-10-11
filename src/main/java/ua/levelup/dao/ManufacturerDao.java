package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Manufacturer;

import java.util.List;

public interface ManufacturerDao {

    void add(@NonNull Manufacturer manufacturer) throws ApplicationException;

    void update(@NonNull Manufacturer manufacturer) throws ApplicationException;

    void delete(int id) throws ApplicationException;

    Manufacturer getById(int id) throws ApplicationException;

    List<Manufacturer> getAllByCategoryId(int categoryId) throws ApplicationException;

    List<Manufacturer> getAll() throws ApplicationException;
}
