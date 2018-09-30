package ua.levelup.config;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactory(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection create() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> connectionWrapper) throws Exception {
        if(!connectionWrapper.getObject().isClosed()){
            connectionWrapper.getObject().close();
        }
    }
}
