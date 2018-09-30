package ua.levelup.mapper;

import ua.levelup.model.OrderPosition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderPositionMapper extends AbstractMapper<OrderPosition> {

    private final static String PRODUCT_ID = "product_id";
    private final static String QUANTITY = "order_product_quantity";
    private final static String PRICE = "order_product_unit_price";
    private final static String PRODUCT_NAME = "product_name";

    @Override
    protected OrderPosition getObject(ResultSet resultSet) throws SQLException {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setProductId(resultSet.getInt(PRODUCT_ID));
        orderPosition.setQuantity(resultSet.getInt(QUANTITY));
        orderPosition.setUnitPrice(resultSet.getFloat(PRICE));
        orderPosition.setProductName(resultSet.getString(PRODUCT_NAME));
        return orderPosition;
    }
}
