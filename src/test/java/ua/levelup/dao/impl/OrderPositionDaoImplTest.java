//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.*;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageHolder;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import ua.levelup.model.*;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//public class OrderPositionDaoImplTest extends AbstractDaoImplTest {
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    private OrderPositionDao orderPositionDao = new OrderPositionDaoImpl(getConnection());
//    private ProductDao productDao = new ProductDaoImpl(getConnection());
//    private OrderPosition orderPosition;
//    private Product firstProduct;
//    private Product secondProduct;
//    private Order order;
//
//    @Before
//    public void init() {
//        UserDao userDao = new UserDaoImpl(getConnection());
//        CategoryDao categoryDao = new CategoryDaoImpl(getConnection());
//        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl(getConnection());
//        OrderDao orderDao = new OrderDaoImpl(getConnection());
//
//        Category category = categoryDao.add(new Category("Category", 0));
//        Manufacturer manufacturer = manufacturerDao.add(new Manufacturer("Manufacturer"));
//        firstProduct = productDao.add(new Product("first", 1.0f, true,
//                "first description", category.getId(), manufacturer.getId()));
//        secondProduct = productDao.add(new Product("second", 1.1f, true,
//                "second description", category.getId(), manufacturer.getId()));
//        User user = userDao.add(new User("user", "password",
//                "email@gamil.com", 0));
//        order = orderDao.add(new Order(user.getId(), 1,
//                new Timestamp(System.currentTimeMillis()), "address", 1));
//        orderPosition = new OrderPosition(order.getId(), firstProduct.getId(), firstProduct.getName(),
//                1, firstProduct.getPrice());
//        order.addOrderPosition(orderPosition);
//    }
//
//    @Test
//    public void addAndGetTest() throws Exception {
//        //WHEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//        OrderPosition extractedFromDbOrderPosition = orderPositionDao.getByPrimaryKey(order.getId(),
//                firstProduct.getId());
//        //THEN
//        Assert.assertNotNull(extractedFromDbOrderPosition);
//        Assert.assertEquals(orderPosition, extractedFromDbOrderPosition);
//    }
//
//    @Test
//    public void addTest_whenNotUniqueOrderPosition_thenException() throws Exception {
//        //GIVEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_ORDER_POSITION_LIST"));
//        //WHEN-THEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//    }
//
//    @Test
//    public void addTest_whenNonexistentOrder_thenException() throws Exception {
//        //GIVEN
//        order.setId(order.getId() + 999);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_ORDER_POSITION_LIST"));
//        //WHEN-THEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//    }
//
//    @Test
//    public void addTest_whenNonexistentProduct_thenException() throws Exception {
//        //GIVEN
//        orderPosition.setProductId(secondProduct.getId() + 999);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_ORDER_POSITION_LIST"));
//        //WHEN-THEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        //GIVEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//        //WHEN
//        int count = orderPositionDao.delete(order.getId(), orderPosition.getProductId());
//        //THEN
//        Assert.assertEquals(1, count);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("EMPTY_RESULTSET") + OrderPosition.class);
//        orderPositionDao.getByPrimaryKey(order.getId(), orderPosition.getProductId());
//    }
//
//    @Test
//    public void updateTest() throws Exception {
//        //GIVEN
//        orderPositionDao.addAll(order.getOrderPositionList());
//        orderPosition.setQuantity(2);
//        orderPosition.setUnitPrice(2.0f);
//        //WHEN
//        int count = orderPositionDao.update(order.getId(), orderPosition.getProductId(), orderPosition);
//        OrderPosition extractedFromDb = orderPositionDao.getByPrimaryKey(order.getId(),
//                orderPosition.getProductId());
//        //THEN
//        Assert.assertEquals(1, count);
//        Assert.assertNotNull(extractedFromDb);
//        Assert.assertEquals(orderPosition, extractedFromDb);
//    }
//
//    @Test
//    public void deleteAllByOrderIdTest() throws Exception {
//        //GIVEN
//        OrderPosition secondOrderPosition = new OrderPosition(order.getId(), secondProduct.getId(),
//                secondProduct.getName(), 1, secondProduct.getPrice());
//        order.addOrderPosition(secondOrderPosition);
//        orderPositionDao.addAll(order.getOrderPositionList());
//        //WHEN
//        int count = orderPositionDao.deleteAllByOrderId(order.getId());
//        List<OrderPosition> orderPositions = orderPositionDao.getAllByOrderId(order.getId());
//        //THEN
//        Assert.assertEquals(2, count);
//        Assert.assertEquals(0, orderPositions.size());
//    }
//
//    @Test
//    public void getAllByOrderIdTest() throws Exception {
//        //GIVEN
//        OrderPosition secondOrderPosition = new OrderPosition(order.getId(), secondProduct.getId(),
//                secondProduct.getName(), 1, secondProduct.getPrice());
//        order.addOrderPosition(secondOrderPosition);
//        orderPositionDao.addAll(order.getOrderPositionList());
//        //WHEN
//        List<OrderPosition> orderPositions = orderPositionDao.getAllByOrderId(order.getId());
//        //THEN
//        Assert.assertEquals(2, orderPositions.size());
//        Assert.assertEquals(orderPosition, orderPositions.get(0));
//        Assert.assertEquals(secondOrderPosition, orderPositions.get(1));
//    }
//}