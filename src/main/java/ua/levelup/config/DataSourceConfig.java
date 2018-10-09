package ua.levelup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Properties;

//Класс конфигурации источника данных для JdbcTemplate
@Configuration
public class DataSourceConfig {

    @Autowired
    private Properties applicationProperties;

    @Bean
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationProperties.getProperty("driver"));
        dataSource.setUrl(applicationProperties.getProperty("url"));
        dataSource.setUsername(applicationProperties.getProperty("username"));
        dataSource.setPassword(applicationProperties.getProperty("password"));
        return dataSource;
    }
}