package ua.levelup.config;

import org.apache.commons.pool2.PooledObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.factory.ConnectionFactory;
import ua.levelup.testconfig.TestContextConfig;

import java.sql.Connection;

/*Класс ConnectionFactoryTest содержит интеграционные тесты для проверки
* работы методов класса ConnectionFactory
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ConnectionFactoryTest {

    @Autowired
    private ConnectionFactory connectionFactory;

    /*Сценарий: Получение объекта класса Connection;
    * Результат: Метод возвращает объект класса Connection.
    * */
    @Test
    public void createTest() throws Exception {
        //WHEN
        Connection connection = connectionFactory.create();
        //THEN
        Assert.assertNotNull(connection);
    }

    /*Сценарий: Получение объекта класса PooledObject<Connection>;
    * Результат: Метод возвращает объект класса PooledObject<Connection>.
    * */
    @Test
    public void wrapTest() throws Exception {
        //GIVEN
        Connection connection = connectionFactory.create();
        //WHEN
        PooledObject<Connection> pooledObject = connectionFactory.wrap(connection);
        Connection otherReference = pooledObject.getObject();
        //THEN
        Assert.assertNotNull(pooledObject);
        Assert.assertSame(connection, otherReference);
    }

    /*Сценарий: Закрытие объекта класса Connection;
    * Результат: Метод закрывает (close()) объект класса Connection.
    * */
    @Test
    public void destroyObjectTest() throws Exception {
        //GIVEN
        Connection connection = connectionFactory.create();
        PooledObject<Connection> pooledObject = connectionFactory.wrap(connection);
        //WHEN
        connectionFactory.destroyObject(pooledObject);
        //THEN
        Assert.assertNotNull(connection);
        Assert.assertTrue(connection.isClosed());
    }
}