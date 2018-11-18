package ua.levelup.dao.config;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

//По заданию работу с одной из сущностей надо реализвать на "чистом" JDBC
//Класс пула соединений для работы на "чистом" JDBC
@Component("connectionFactory")
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

//    @Autowired
//    private Properties applicationProperties;

    @Autowired
    private DataSource dataSource;

    //По заданию работа с одной из сущностей должна быть организована на "чистом" JDBC.
    //Кроме этого, в проекте в демонстрационных целях используется встроенная база данных H2.
    //Учитывая то что база данных будет хранится только в оперативной памяти, нам надо
    //обеспечить "один вход" в эту бд. Поэтому при переопределении метода
    //public Connection create() нам не подходит стандартный подход и использованием
    //DriverManager. Вместо этого, мы должны использовать уже созданный бин dataSource.
//    @Override
//    public Connection create() throws Exception {
//        Class.forName(applicationProperties.getProperty("driver"));
//        String url = applicationProperties.getProperty("url");
//        String username = applicationProperties.getProperty("username");
//        String password = applicationProperties.getProperty("password");
//        return DriverManager.getConnection(url, username, password);
//    }

    @Override
    public Connection create() throws Exception {
        return dataSource.getConnection();
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