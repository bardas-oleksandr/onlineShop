package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionPoolFactoryTest {
    @Test
    public void getConnectionPoolTest() throws Exception {
        //WHEN
        GenericObjectPool<Connection> pool = ConnectionPoolHolder.getConnectionPool();
        //THEN
        Assert.assertNotNull(pool);
    }
}