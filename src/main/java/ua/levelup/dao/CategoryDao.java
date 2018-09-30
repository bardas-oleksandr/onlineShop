package ua.levelup.dao;

import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Category;

import java.util.List;

public interface CategoryDao {

    Category add(Category category) throws ApplicationException;

    int update(Category category) throws ApplicationException;

    int delete(int id) throws ApplicationException;

    Category getById(int id) throws ApplicationException;

    int deleteAllByParentId(int parentId) throws ApplicationException;

    List<Category> getAllByParentId(int parentId) throws ApplicationException;

    List<Category> getAllByLevel(int level) throws ApplicationException;
}
