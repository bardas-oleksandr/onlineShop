//package ua.levelup.mapper;
//
//import ua.levelup.model.Order;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class OrderMapper extends AbstractMapper<Order> {
//
//    private final static String ID = "id";
//    private final static String USER_ID = "order_user_id";
//    private final static String USER_NAME = "user_name";
//    private final static String STATE = "order_state";
//    private final static String DATE = "order_date";
//    private final static String ADDRESS = "order_address";
//    private final static String PAYMENT_CONDITIONS = "order_payment_conditions";
//
//    @Override
//    protected Order getObject(ResultSet resultSet) throws SQLException {
//        Order order = new Order();
//        order.setId(resultSet.getInt(ID));
//
//
//
//
//        //order.setUserId(resultSet.getInt(USER_ID));
//        //order.setUserName(resultSet.getString(USER_NAME));
//
//
//
//
//
//        order.setOrderState(resultSet.getInt(STATE));
//        order.setDate(resultSet.getTimestamp(DATE));
//        order.setAddress(resultSet.getString(ADDRESS));
//        order.setPaymentConditions(resultSet.getInt(PAYMENT_CONDITIONS));
//        order.setOrderPositionList(new ArrayList<>());
//        return order;
//    }
//}
