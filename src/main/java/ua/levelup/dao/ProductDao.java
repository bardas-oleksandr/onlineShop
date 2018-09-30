package ua.levelup.dao;

import ua.levelup.dao.support.OrderMethod;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Product;

import java.util.List;

public interface ProductDao {

    Product add(Product product) throws ApplicationException;

    int update(Product product) throws ApplicationException;

    int delete(int id) throws ApplicationException;

    Product getById(int id) throws ApplicationException;

    List<Product> getFilteredProducts(Product product, float minPrice, float maxPrice, OrderMethod method) throws ApplicationException;
}