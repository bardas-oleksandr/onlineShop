package ua.levelup.dao.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.OrderDao;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Order;
import ua.levelup.model.User;
import ua.levelup.testconfig.TestContextConfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс OrderDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Заказ"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class OrderDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Properties messagesProperties;

    private Order order;

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - все поля корректны.
    * Результат: заказ товаров успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenOrderIsCorrect_thenOk() throws Exception {
        //GIVEN
        User user = userDao.getById(1);
        user.setPassword(null);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        //WHEN
        orderDao.add(order);
        Order extracted = orderDao.getById(order.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(order, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - поле user == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        order = new Order(null, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - пользователь user с заданным id не существует в базе данных.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenUserNonexistent_thenException() throws Exception {
        //GIVEN
        User user = new User();
        user.setId(10);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - поле orderState == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenOrderStateEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User();
        user.setId(1);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - поле paymentConditions == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenPaymentConditionsEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User();
        user.setId(1);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        order.setPaymentConditions(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - поле address == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenAddressEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User();
        user.setId(1);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        order.setAddress(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - поле date == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void addTest_whenDateEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User();
        user.setId(1);
        order = new Order(user, "address", new Timestamp(1), Order.PaymentConditions.CARD);
        order.setPayed(false);
        order.setOrderState(Order.OrderState.REGISTERED);
        order.setDate(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.add(order);
    }

    /*Сценарий: - добавление в базу данных информации о заказе товаров;
    *           - объект вставляемого заказа равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenOrderEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("order is marked @NonNull but is null");
        //THEN
        orderDao.add(null);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - все новые значения полей корректны.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenAllCorrect_thenOk() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setAddress("new address");
        order.setPayed(true);
        User user = userDao.getById(2);
        user.setPassword(null);
        order.setUser(user);
        order.setPaymentConditions(Order.PaymentConditions.CASH);
        order.setOrderState(Order.OrderState.CANCELED);
        //WHEN
        orderDao.update(order);
        //THEN
        Order extracted = orderDao.getById(1);
        Assert.assertEquals(order,extracted);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - заказ с указанным ID не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenOrderNonexistent_thenOk() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setId(10);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_ORDER_NONEXISTENT"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - объект вставляемого заказа равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void updateTest_whenOrderEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("order is marked @NonNull but is null");
        //WHEN-THEN
        orderDao.update(null);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - пользователя с заданным ID не существует
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenUserNonexistent_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        User user = new User();
        user.setId(10);
        order.setUser(user);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - поле user == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setUser(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - поле address == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenAddressEqualsNull_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setAddress(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - поле date == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenDateEqualsNull_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setDate(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - поле orderState == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenOrderStateEqualsNull_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setOrderState(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - модификация в базе данных информации о заказе товаров;
    *           - поле paymentConditions == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void updateTest_whenPaymentConditionsEqualsNull_thenException() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        order.setPaymentConditions(null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"));
        //WHEN-THEN
        orderDao.update(order);
    }

    /*Сценарий: - удаление из базы данных информации о заказе товаров;
    *           - заказ с заданным ID существует в базе данных.
    *           - в базе данных существуют связанные с заказом строки
    *           таблицы orders_products
    * Результат: операция выполнена успешно. Связанные строки из таблицы
    * orders_products также удалены.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_whenOrderIsNotEmpty_thenOk() throws Exception {
        //GIVEN
        order = orderDao.getById(1);
        //WHEN
        orderDao.delete(order.getId());
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Order.class);
        orderDao.getById(order.getId());
    }

    /*Сценарий: - удаление из базы данных информации о заказе товаров;
    *           - заказ с заданным ID не существует в базе данных.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void deleteTest_whenOrderNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_ORDER_NONEXISTENT"));
        //WHEN-THEN
        orderDao.delete(10);
    }

    /*Сценарий: - получение из базы данных информации о заказе товаров;
    *           - заказ с заданным ID существует в базе данных.
    *           - в базе данных существуют связанные с заказом строки
    *           таблицы orders_products
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByIdTest_whenOrderIsNotEmpty_thenOk() throws Exception {
        //GIVEN
        List<Order> allOrders = orderDao.getAll();
        //WHEN
        order = orderDao.getById(1);
        //THEN
        Assert.assertNotNull(order);
        Assert.assertTrue(allOrders.contains(order));
    }

    /*Сценарий: - получение из базы данных информации о заказе товаров;
    *           - заказ с заданным ID существует в базе данных.
    *           - в базе данных не существуют связанные с заказом строки
    *           таблицы orders_products
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByIdTest_whenOrderIsEmpty_thenOk() throws Exception {
        //GIVEN
        List<Order> allOrders = orderDao.getAll();
        //WHEN
        order = orderDao.getById(3);
        //THEN
        Assert.assertNotNull(order);
        Assert.assertTrue(allOrders.contains(order));
    }

    /*Сценарий: - получение из базы данных информации о заказе товаров;
    *           - заказ с заданным ID не существует в базе данных.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getByIdTest_whenOrderNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Order.class);
        //WHEN-THEN
        order = orderDao.getById(10);;
    }

    /*Сценарий: - получение из базы данных информации о всех заказах
    *           для пользователя с заданным ID
    *           - в базе данных есть заказы заданного пользователя
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllByUserIdTest_whenOrdersExist_thenOk() throws Exception {
        //GIVEN
        List<Order> allOrders = orderDao.getAll();
        //WHEN
        List<Order> extracted = orderDao.getAllByUserId(1);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(2, extracted.size());
        Assert.assertTrue(allOrders.contains(extracted.get(0)));
        Assert.assertTrue(allOrders.contains(extracted.get(1)));
    }

    /*Сценарий: - получение из базы данных информации о всех заказах
    *           для пользователя с заданным ID
    *           - в базе данных нет заказов заданного пользователя
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllByUserIdTest_whenOrdersNonexistent_thenOk() throws Exception {
        //WHEN
        List<Order> extracted = orderDao.getAllByUserId(3);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(0, extracted.size());
    }

    /*Сценарий: - получение из базы данных информации о всех заказах
    *           для пользователя с заданным ID
    *           - в базе данных нет пользователя с указанным ID
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllByUserIdTest_whenUserNonexistent_thenOk() throws Exception {
        //WHEN
        List<Order> extracted = orderDao.getAllByUserId(4);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(0, extracted.size());
    }

    /*Сценарий: - получение из базы данных информации о всех заказах
    *           - в базе данных есть заказы
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderTest.sql"})
    public void getAllTest_whenOrdersExist_thenOk() throws Exception {
        //GIVEN
        List<Order> expected = new ArrayList<>();
        expected.add(orderDao.getById(1));
        expected.add(orderDao.getById(2));
        expected.add(orderDao.getById(3));
        //WHEN
        List<Order> extracted = orderDao.getAll();
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(expected, extracted);
    }

    /*Сценарий: - получение из базы данных информации о всех заказах
    *           - в базе данных нет заказы
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllTest_whenOrdersNonexistent_thenOk() throws Exception {
        //WHEN
        List<Order> extracted = orderDao.getAll();
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(0, extracted.size());
    }
}