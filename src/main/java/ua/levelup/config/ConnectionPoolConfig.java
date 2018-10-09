package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

//По заданию работу с одной из сущностей надо реализвать на "чистом" JDBC
//Класс конфигурации пула соединений для работы с JDBC
@Configuration
public class ConnectionPoolConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    //Scope по умолчанию - singleton, но можно было бы его изменить, например так:
    //@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name="connectionPool")
    public GenericObjectPool<Connection> genericObjectPool(){
        return new GenericObjectPool<Connection>(connectionFactory);
    }
}