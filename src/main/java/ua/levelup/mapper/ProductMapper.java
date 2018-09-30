package ua.levelup.mapper;

import ua.levelup.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper extends AbstractMapper<Product> {

    private final static String ID = "products.id";
    private final static String NAME = "products.product_name";
    private final static String PRICE = "products.product_price";
    private final static String AVAILABLE = "products.product_available";
    private final static String DESCRIPTION = "products.product_description";
    private final static String CATEGORY_ID = "products.product_category_id";
    private final static String CATEGORY_NAME = "categories.category_name";
    private final static String MANUFACTURER_ID = "products.product_manufacturer_id";
    private final static String MANUFACTURER_NAME = "manufacturers.manufacturer_name";

    @Override
    protected Product getObject(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt(ID));
        product.setName(resultSet.getString(NAME));
        product.setPrice(resultSet.getFloat(PRICE));
        product.setAvailable(resultSet.getBoolean(AVAILABLE));
        product.setDescription(resultSet.getString(DESCRIPTION));
        product.setCategoryId(resultSet.getInt(CATEGORY_ID));
        product.setManufacturerId(resultSet.getInt(MANUFACTURER_ID));
        product.setCategoryName(resultSet.getString(CATEGORY_NAME));
        product.setManufacturerName(resultSet.getString(MANUFACTURER_NAME));
        return product;
    }
}
