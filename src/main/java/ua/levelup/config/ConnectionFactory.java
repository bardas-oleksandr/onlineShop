package ua.levelup.config;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//По заданию работу с одной из сущностей надо реализвать на "чистом" JDBC
//Класс пула соединений для работы на "чистом" JDBC
@Component("connectionFactory")
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    @Autowired
    private Properties applicationProperties;

    @Override
    public Connection create() throws Exception {
        Class.forName(applicationProperties.getProperty("driver"));
        String url = applicationProperties.getProperty("url");
        String username = applicationProperties.getProperty("username");
        String password = applicationProperties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> connectionWrapper) throws Exception {
        if (!connectionWrapper.getObject().isClosed()) {
            connectionWrapper.getObject().close();
        }
    }
}