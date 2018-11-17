package ua.levelup.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ua.levelup.converter.fromdto.*;
import ua.levelup.converter.todto.*;
import ua.levelup.dao.config.ConnectionFactory;
import ua.levelup.exception.ApplicationException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**Корневая конфигурация веб-контекста
 * Автор: Бардась А. А.
 */
@Configuration
@ComponentScan("ua.levelup.dao")
@ComponentScan("ua.levelup.service")
@ComponentScan("ua.levelup.converter")
@ComponentScan("ua.levelup.web.dto")
@ComponentScan("ua.levelup.validator")
@Import(SecurityConfig.class)
public class ApplicationConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private GenericObjectPool<Connection> connectionPool;

    @Autowired
    private Set<Converter<?,?>> converterSet;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean("bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //---------------Конфигурирование конвертеров--------------------------------
    //Бин для получения конвертеров
    //В Spring MVC уже есть встроенный бин mvcConversionService,
    //поэтому нам не нужно создавать еще один.
    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionServiceFactoryBean(){
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(converterSet);
        return factoryBean;
    }

    @Bean("converterSet")
    public Set<Converter<?,?>> converterSet(){
        Set<Converter<?,?>> converterSet = new HashSet<>();
        converterSet.add(new CategoryCreateDtoConverter());
        converterSet.add(new CredentialsCreateDtoConverter());
        converterSet.add(new ManufacturerCreateDtoConverter());
        converterSet.add(new OrderCreateDtoConverter());
        converterSet.add(new OrderPositionCreateDtoConverter());
        converterSet.add(new ProductCreateDtoConverter());
        converterSet.add(new ProductInCartCreateDtoConverter());
        converterSet.add(new SearchParamsCreateDtoConverter());
        converterSet.add(new UserCreateDtoConverter());
        converterSet.add(new CartConverter());
        converterSet.add(new CategoryConverter());
        converterSet.add(new ManufacturerConverter());
        converterSet.add(new OrderConverter());
        converterSet.add(new OrderPositionConverter());
        converterSet.add(new ProductConverter());
        converterSet.add(new ProductInCartConverter());
        converterSet.add(new UserConverter());
        return converterSet;
    }

    //------------Конфигурирование валидаторов-------------------------------------
    @Bean("validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

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