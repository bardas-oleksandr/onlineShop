package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;

public enum ConnectionPoolHolder {
    ;

    private final static String DRIVER = PropertiesManager.getApplicationProperties().getProperty("driver");
    private final static String URL = PropertiesManager.getApplicationProperties().getProperty("url");
    private final static String USER = PropertiesManager.getApplicationProperties().getProperty("username");
    private final static String PASSWORD = PropertiesManager.getApplicationProperties().getProperty("password");
    private static GenericObjectPool<Connection> connectionPool = new GenericObjectPool<Connection>(
            new ConnectionFactory(DRIVER, URL, USER, PASSWORD));

    public static GenericObjectPool<Connection> getConnectionPool() {
        return connectionPool;
    }
}
