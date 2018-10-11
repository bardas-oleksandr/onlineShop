package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Product;
import ua.levelup.model.SearchParams;

import java.util.List;

public interface ProductDao {

    Product add(Product product) throws ApplicationException;

    int update(Product product) throws ApplicationException;

    int delete(int id) throws ApplicationException;

    Product getById(int id) throws ApplicationException;

    List<Product> getFilteredProducts(SearchParams searchParams) throws ApplicationException;
}