package ua.levelup.testconfig;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.levelup.config.ApplicationConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//При xml-конфигурировании мы бы использовали аннотацию @ImportResource
//@ImportResource({"classpath:context/app-context.xml"})
@Configuration
@Import(ApplicationConfig.class)
@Profile("test")
public class TestContextConfig {

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