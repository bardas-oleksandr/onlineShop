package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Product;
import ua.levelup.model.SearchParams;

import java.util.List;

public interface ProductDao {

    void add(@NonNull Product product) throws ApplicationException;

    void update(@NonNull Product product) throws ApplicationException;

    void delete(int id) throws ApplicationException;

    Product getById(int id) throws ApplicationException;

    List<Product> getFilteredProducts(@NonNull SearchParams searchParams) throws ApplicationException;
}