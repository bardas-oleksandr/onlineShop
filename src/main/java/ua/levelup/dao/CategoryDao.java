package ua.levelup.dao;

import lombok.NonNull;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Category;

import java.util.List;

public interface CategoryDao {

    void add(@NonNull Category category) throws ApplicationException;

    void update(@NonNull Category category) throws ApplicationException;

    void delete(int id) throws ApplicationException;

    Category getById(int id) throws ApplicationException;

    int deleteAllByParentId(int parentId) throws ApplicationException;

    List<Category> getAllByParentId(int parentId) throws ApplicationException;

    List<Category> getAllByLevel(int level) throws ApplicationException;
}
