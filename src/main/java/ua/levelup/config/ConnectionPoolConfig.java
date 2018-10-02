package ua.levelup.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;

//По заданию работу с одной из сущностей надо реализвать на "чистом" JDBC
//Класс конфигурации пула соединений для работы с JDBC
@Configuration
public class ConnectionPoolConfig {

    @Getter
    @Setter
    @Resource(name="connectionFactory")
    private ConnectionFactory connectionFactory;

    @Getter
    private GenericObjectPool<Connection> connectionPool;

    @PostConstruct
    void init(){
        connectionPool = new GenericObjectPool<Connection>(connectionFactory);
    }
}
