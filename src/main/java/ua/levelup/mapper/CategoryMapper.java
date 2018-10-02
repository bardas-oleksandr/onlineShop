package ua.levelup.mapper;

import ua.levelup.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper extends AbstractMapper<Category> {

    private final static String ID = "id";
    private final static String NAME = "category_name";
    private final static String PARENT_ID = "category_parent_id";

    @Override
    protected Category getObject(ResultSet resultSet) throws SQLException{
        Category category = new Category();
        category.setId(resultSet.getInt(ID));
        category.setName(resultSet.getString(NAME));


        //category.setParentId(resultSet.getInt(PARENT_ID));



        return category;
    }
}
