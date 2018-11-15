package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.levelup.dao.config.ConnectionFactory;
import ua.levelup.exception.ApplicationException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**Корневая конфигурация веб-контекста
 * Автор: Бардась А. А.
 */
@Configuration
@ComponentScan("ua.levelup.dao")
public class ApplicationConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private GenericObjectPool<Connection> connectionPool;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean("applicationProperties")
    public Properties applicationProperties(){
        return loadProperties("application.properties");
    }

    @Bean("messagesProperties")
    public Properties exceptionMessagesProperties(){
        return loadProperties(applicationProperties()
                .getProperty("exception.messages.properties"));
    }

    @Bean(name="connectionPool")
    public GenericObjectPool<Connection> genericObjectPool(){
        return new GenericObjectPool<Connection>(connectionFactory);
    }

    @Bean("connection")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Profile("!test")
    public Connection connection() throws Exception{
        return connectionPool.borrowObject();
    }

    @Bean("dataSource")
    @Profile("!test")
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationProperties().getProperty("driver"));
        dataSource.setUrl(applicationProperties().getProperty("url"));
        dataSource.setUsername(applicationProperties().getProperty("username"));
        dataSource.setPassword(applicationProperties().getProperty("password"));
        return dataSource;
    }

    @Bean("namedParameterJdbcTemplate")
    @Profile("!test")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private Properties loadProperties(String fileName){
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new ApplicationException("Failed to read file: " + fileName, e);
        }
        return properties;
    }
}