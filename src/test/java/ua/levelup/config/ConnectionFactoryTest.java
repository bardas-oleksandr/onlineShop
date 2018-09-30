package ua.levelup.config;

import org.apache.commons.pool2.PooledObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionFactoryTest {
    private static final String DRIVER = PropertiesManager.getApplicationProperties()
            .getProperty("test.driver");
    private static final String URL = PropertiesManager.getApplicationProperties()
            .getProperty("test.url");
    private static final String USER = PropertiesManager.getApplicationProperties()
            .getProperty("test.username");
    private static final String PASSWORD = PropertiesManager.getApplicationProperties()
            .getProperty("test.password");

    private ConnectionFactory factory;

    @Before
    public void init(){
        factory = new ConnectionFactory(DRIVER, URL, USER, PASSWORD);
    }

    @Test
    public void createTest() throws Exception {
        //WHEN
        Connection connection = factory.create();
        //THEN
        Assert.assertNotNull(connection);
    }

    @Test
    public void wrapTest() throws Exception {
        //GIVEN
        Connection connection = factory.create();
        //WHEN
        PooledObject<Connection> pooledObject = factory.wrap(connection);
        Connection otherReference = pooledObject.getObject();
        //THEN
        Assert.assertNotNull(pooledObject);
        Assert.assertSame(connection,otherReference);
    }

    @Test
    public void destroyObjectTest() throws Exception {
        //GIVEN
        Connection connection = factory.create();
        PooledObject<Connection> pooledObject = factory.wrap(connection);
        //WHEN
        factory.destroyObject(pooledObject);
        //THEN
        Assert.assertNotNull(connection);
        Assert.assertTrue(connection.isClosed());
    }
}