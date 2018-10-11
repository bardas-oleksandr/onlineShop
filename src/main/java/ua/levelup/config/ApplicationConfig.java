package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ua.levelup.exception.ApplicationException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.Set;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Properties applicationProperties;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Set<Converter<?,?>> converterSet;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    //Scope по умолчанию - singleton, но можно было бы его изменить, например так:
    //@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name="connectionPool")
    public GenericObjectPool<Connection> genericObjectPool(){
        return new GenericObjectPool<Connection>(connectionFactory);
    }

    @Bean("conversionServiceFactoryBean")
    ConversionServiceFactoryBean conversionServiceFactoryBean(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converterSet);
        return new ConversionServiceFactoryBean();
    }

    @Bean("dataSource")
    @Profile("!test")
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationProperties.getProperty("driver"));
        dataSource.setUrl(applicationProperties.getProperty("url"));
        dataSource.setUsername(applicationProperties.getProperty("username"));
        dataSource.setPassword(applicationProperties.getProperty("password"));
        return dataSource;
    }

    @Bean("messagesProperties")
    public Properties exceptionMessagesProperties(){
        String exceptionMessagesFileName = applicationProperties
                .getProperty("exception.messages.properties");
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(exceptionMessagesFileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new ApplicationException("Failed to read file: " + exceptionMessagesFileName, e);
        }
        return properties;
    }

    @Bean("validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

    @Bean("namedParameterJdbcTemplate")
    @Profile("!test")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}