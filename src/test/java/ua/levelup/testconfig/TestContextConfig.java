package ua.levelup.testconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan("ua.levelup.converter.*")
@ComponentScan("ua.levelup.validator.*")
@ComponentScan("ua.levelup.dao.*")
@ImportResource({"classpath:context/app-context.xml"})
@Profile("test")
public class TestContextConfig {

    @Autowired
    private Properties applicationProperties;

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