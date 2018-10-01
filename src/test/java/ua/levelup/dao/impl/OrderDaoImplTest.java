package ua.levelup.dao.impl;

import ua.levelup.dao.OrderDao;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Order;
import ua.levelup.model.User;
import ua.levelup.model.support.OrderState;
import ua.levelup.model.support.PaymentConditions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Timestamp;
import java.util.List;

public class OrderDaoImplTest extends AbstractDaoImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final String ADDRESS = "address";
    private final Timestamp DATE = new Timestamp(System.currentTimeMillis());
    private User user = new User("user", "password", "email@gmail.com", 0);
    private OrderDao orderDao;
    private Order order;

    @Before
    public void init() {
        UserDao userDao = new UserDaoImpl(getConnection());
        user = userDao.add(user);
        orderDao = new OrderDaoImpl(getConnection());
        order = new Order(user.getId(), 0, DATE, ADDRESS, 0);
        order.setUserName(user.getUserName());
    }

    @Test
    public void addAndGetTest() throws Exception {
        //WHEN
        Order returnedOrder = orderDao.add(order);
        Order extractedFromDbOrder = orderDao.getById(returnedOrder.getId());
        //THEN
        Assert.assertNotNull(returnedOrder);
        Assert.assertNotNull(extractedFromDbOrder);
        Assert.assertSame(order, returnedOrder);
        Assert.assertEquals(order, extractedFromDbOrder);
    }

    @Test
    public void addTest_whenNonexistentUser_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("FAILED_INSERT_ORDER"));
        //WHEN-THEN
        orderDao.add(new Order(user.getId() + 999, 0, DATE, ADDRESS, 0));
    }

    @Test
    public void deleteTest() throws Exception {
        //GIVEN
        orderDao.add(order);
        //WHEN
        int count = orderDao.delete(order.getId());
        //THEN
        Assert.assertEquals(1, count);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("EMPTY_RESULTSET") + Order.class);
        Order extractedFromDb = orderDao.getById(order.getId());
    }

    @Test
    public void updateTest() throws Exception {
        //GIVEN
        order = orderDao.add(order);
        order.setUserId(user.getId());
        order.setOrderState(OrderState.PAYED.ordinal());
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setAddress("new address");
        order.setPaymentConditions(PaymentConditions.CASH.ordinal());
        //WHEN
        int count = orderDao.update(order);
        Order extractedFromDb = orderDao.getById(order.getId());
        //THEN
        Assert.assertEquals(1, count);
        Assert.assertNotNull(extractedFromDb);
        Assert.assertEquals(order, extractedFromDb);
    }

    @Test
    public void getAllByUserIdTest() throws Exception {
        //GIVEN
        Order secondOrder = new Order(user.getId(), 0, DATE, ADDRESS, 1);
        secondOrder.setUserName(user.getUserName());
        order = orderDao.add(order);
        secondOrder = orderDao.add(secondOrder);
        //WHEN
        List<Order> orders = orderDao.getAllByUserId(user.getId());
        //THEN
        Assert.assertEquals(2, orders.size());
        Assert.assertEquals(order, orders.get(0));
        Assert.assertEquals(secondOrder, orders.get(1));
    }

    @Test
    public void getAllTest() throws Exception {
        //GIVEN
        Order secondOrder = new Order(user.getId(), 0, DATE, ADDRESS, 1);
        secondOrder.setUserName(user.getUserName());
        order = orderDao.add(order);
        secondOrder = orderDao.add(secondOrder);
        //WHEN
        List<Order> orders = orderDao.getAll();
        //THEN
        Assert.assertEquals(2, orders.size());
        Assert.assertEquals(order, orders.get(0));
        Assert.assertEquals(secondOrder, orders.get(1));
    }
}