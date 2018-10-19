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
import ua.levelup.dao.OrderPositionDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.OrderPosition;
import ua.levelup.testconfig.TestContextConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс OrderPositionDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Заказанный товар" (товар определенного вида и количества, находящийся в заказе)
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class OrderPositionDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private OrderPositionDao orderPositionDao;

    @Autowired
    private Properties messagesProperties;

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - все поля корректны;
    * Результат: товарная позиция успешно добавлена в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenOrderPositionIsCorrect_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 3;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, 1, 2, 3.0f);
        orderPosition.setProductName("first product");
        orderPositionList.add(orderPosition);
        orderPosition = new OrderPosition(ORDER_ID, 2, 3, 4.0f);
        orderPosition.setProductName("second product");
        orderPositionList.add(orderPosition);
        //WHEN
        orderPositionDao.addAll(orderPositionList);
        List<OrderPosition> extractedList = orderPositionDao.getAllByOrderId(ORDER_ID);
        //THEN
        Assert.assertEquals(orderPositionList.size(), extractedList.size());
        Assert.assertTrue(orderPositionList.contains(extractedList.get(0)));
        Assert.assertTrue(orderPositionList.contains(extractedList.get(1)));
    }

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - в базе данных нет соответсвующего заказа;
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenOrderNonexistent_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 4;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, 1, 2, 3.0f);
        orderPosition.setProductName("first product");
        orderPositionList.add(orderPosition);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.addAll(orderPositionList);
    }

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - в базу данных добавляется две товарные позиции с одинаковыми
    *           первичными ключами (спаренный ключи из ID заказа и ID товара)
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenOrderPositionIsNotUnique_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 3;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, 1, 2, 3.0f);
        orderPosition.setProductName("first product");
        orderPositionList.add(orderPosition);
        orderPositionList.add(orderPosition);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.addAll(orderPositionList);
    }

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - количество товара отрицательное
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenQuantityIsNotPositive_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, 1, 0, 3.0f);
        orderPositionList.add(orderPosition);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.addAll(orderPositionList);
    }

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - цена товара отрицательная
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenPriceIsNegative_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, 1, 1, -0.1f);
        orderPositionList.add(orderPosition);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.addAll(orderPositionList);
    }

    /*Сценарий: - добавление в базу данных информации о товарной позиции из заказа;
    *           - List<OrderPosition>, передаваемый в качестве аргумента, равен null
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void addTest_whenOrderPositionListEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("orderPositionList is marked @NonNull but is null");
        //WHEN-THEN
        orderPositionDao.addAll(null);
    }

    /*Сценарий: - модификация в базе данных информации о товарной позиции из заказа;
    *           - в таблице orders_products существует строка, соответсвующая объекту
    *           orderPosition
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void updateTest_whenOrderPositionExists_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        final int PRODUCT_ID = 1;
        OrderPosition orderPosition = orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
        //WHEN
        orderPositionDao.update(orderPosition);
        //THEN
        OrderPosition extracted = orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
        Assert.assertEquals(orderPosition, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о товарной позиции из заказа;
    *           - в таблице orders_products не существует строки, соответсвующей объекту
    *           orderPosition
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void updateTest_whenOrderPositionNonexistent_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 5;
        final int PRODUCT_ID = 5;
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, PRODUCT_ID, 2, 3.0f);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_ORDER_POSITION_NONEXISTENT"));
        //WHEN-THEN
        orderPositionDao.update(orderPosition);
    }

    /*Сценарий: - модификация в базе данных информации о товарной позиции из заказа;
    *           - новое количество товара не положительное
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void updateTest_whenQuantityIsNotPositive_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        final int PRODUCT_ID = 1;
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, PRODUCT_ID, 0, 3.0f);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.update(orderPosition);
    }

    /*Сценарий: - модификация в базе данных информации о товарной позиции из заказа;
    *           - новая цена товара отрицательная
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void updateTest_whenPriceIsNegative_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        final int PRODUCT_ID = 1;
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, PRODUCT_ID, 1, -0.1f);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER_POSITION"));
        //WHEN-THEN
        orderPositionDao.update(orderPosition);
    }

    /*Сценарий: - удаление из базы данных информации о товарной позиции из заказа;
    *           - в таблице orders_products есть информация о товароной позиции с
    *           заданным ключем
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void deleteTest_whenOrderPositionExists_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        final int PRODUCT_ID = 1;
        OrderPosition orderPosition = orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
        //WHEN
        orderPositionDao.delete(ORDER_ID, PRODUCT_ID);
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + OrderPosition.class);
        orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
    }

    /*Сценарий: - удаление из базы данных информации о товарной позиции из заказа;
    *           - в таблице orders_products нет информации о товароной позиции с
    *           заданным ключем
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void deleteTest_whenOrderPositionNonexistent_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 5;
        final int PRODUCT_ID = 5;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_ORDER_POSITION_NONEXISTENT"));
        //WHEN-THEN
        orderPositionDao.delete(ORDER_ID, PRODUCT_ID);
    }

    /*Сценарий: - получение из базы данных информации о товарной позициях
    *           по первичному ключу;
    *           - в таблице orders_products есть информация о товарной позиции
    *           с заданным ключем.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void getAllByPrimaryKeyTest_whenOrderPositionsExist_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 3;
        final int PRODUCT_ID = 1;
        List<OrderPosition> orderPositionList = new ArrayList<>();
        OrderPosition orderPosition = new OrderPosition(ORDER_ID, PRODUCT_ID, 2, 3.0f);
        orderPosition.setProductName("first product");
        orderPositionList.add(orderPosition);
        orderPositionDao.addAll(orderPositionList);
        //WHEN
        orderPosition = orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
        //THEN
        Assert.assertNotNull(orderPosition);
        Assert.assertEquals(orderPositionList.get(0), orderPosition);
    }

    /*Сценарий: - получение из базы данных информации о товарной позициях
    *           по первичному ключу;
    *           - в таблице orders_products нет информации о товарной позиции
    *           с заданным ключем.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void getAllByPrimaryKeyTest_whenOrderPositionsNonexistent_thenException() throws Exception {
        //GIVEN
        final int ORDER_ID = 10;
        final int PRODUCT_ID = 10;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties.getProperty("EMPTY_RESULTSET")
                + OrderPosition.class);
        //WHEN-THEN
        OrderPosition orderPosition = orderPositionDao.getByPrimaryKey(ORDER_ID, PRODUCT_ID);
    }

    /*Сценарий: - удаление из базы данных информации о товарной позициях
    *           для заказа с заданным ID;
    *           - в таблице orders_products есть информация о товарных позициях
    *           для заказа с заданным ID
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void deleteAllByOrderIdTest_whenOrderPositionsExist_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        orderPositionDao.deleteAllByOrderId(ORDER_ID);
        //WHEN
        List<OrderPosition> extracted = orderPositionDao.getAllByOrderId(ORDER_ID);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(0, extracted.size());
    }

    /*Сценарий: - удаление из базы данных информации о товарной позициях
    *           для заказа с заданным ID;
    *           - в таблице orders_products нет информации о товарных позициях
    *           для заказа с заданным ID
    * Результат: операция выполнен успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void deleteAllByOrderIdTest_whenOrderPositionsNonexistent_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 5;
        orderPositionDao.deleteAllByOrderId(ORDER_ID);
        //WHEN
        List<OrderPosition> extracted = orderPositionDao.getAllByOrderId(ORDER_ID);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(0, extracted.size());
    }

    /*Сценарий: - получение из базы данных информации о товарной позициях
    *           для заказа с заданным ID;
    *           - в таблице orders_products есть информация о товарных позициях
    *           для заказа с заданным ID
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void getAllByOrderIdTest_whenOrderPositionsExist_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 1;
        List<OrderPosition> expected = new ArrayList<>();
        expected.add(orderPositionDao.getByPrimaryKey(1, 1));
        expected.add(orderPositionDao.getByPrimaryKey(1, 2));
        //WHEN
        List<OrderPosition> orderPositionList = orderPositionDao.getAllByOrderId(ORDER_ID);
        //THEN
        Assert.assertNotNull(orderPositionList);
        Assert.assertEquals(expected.size(), orderPositionList.size());
        Assert.assertTrue(expected.contains(orderPositionList.get(0)));
        Assert.assertTrue(expected.contains(orderPositionList.get(1)));
    }

    /*Сценарий: - получение из базы данных информации о товарной позициях
    *           для заказа с заданным ID;
    *           - в таблице orders_products нет информации о товарных позициях
    *           для заказа с заданным ID
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_orderPositionTest.sql"})
    public void getAllByOrderIdTest_whenOrderPositionsNonexistent_thenOk() throws Exception {
        //GIVEN
        final int ORDER_ID = 5;
        //WHEN
        List<OrderPosition> orderPositionList = orderPositionDao.getAllByOrderId(ORDER_ID);
        //THEN
        Assert.assertNotNull(orderPositionList);
        Assert.assertEquals(0, orderPositionList.size());
    }
}