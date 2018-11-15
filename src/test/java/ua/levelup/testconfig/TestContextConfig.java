package ua.levelup.testconfig;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ua.levelup.config.ApplicationConfig;
import ua.levelup.config.WebMvcConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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

    //Этот бин надо убрать после доработки тестов пакетов validator и converter.
    @Bean("validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

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