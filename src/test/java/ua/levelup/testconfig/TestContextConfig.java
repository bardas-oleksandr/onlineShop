package ua.levelup.testconfig;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ua.levelup.config.ApplicationConfig;
import ua.levelup.converter.fromdto.*;
import ua.levelup.converter.todto.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

//1.При xml-конфигурировании мы бы использовали аннотацию @ImportResource
//@ImportResource({"classpath:context/app-context.xml"})

//2. Аннотации @ComponentScan надо убрать после того как я переделаю тесты
//пакетов validator и converter.
@Configuration
@ComponentScan("ua.levelup.validator")
@ComponentScan("ua.levelup.converter")
@Import({ApplicationConfig.class})
@Profile("test")
public class TestContextConfig {

    //-------------БИНЫ ИЗ КОНТЕКСТА СЕРВЛЕТА ДИСПЕТЧЕРА-------------------------
    //Эти бины надо убрать после доработки тестов пакетов validator и converter.
    @Bean("validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionServiceFactoryBean(){
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(converterSet());
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
    //-----------------------------------------------------------------------------

    @Bean("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate()
            throws ClassNotFoundException, SQLException {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean("dataSource")
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema_init.sql").build();
    }

    @Bean("connection")
    public Connection connection()throws ClassNotFoundException, SQLException{
        return dataSource().getConnection();
    }
}