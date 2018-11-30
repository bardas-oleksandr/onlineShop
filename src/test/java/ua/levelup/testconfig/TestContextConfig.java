package ua.levelup.testconfig;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.levelup.config.ApplicationConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ContextConfiguration
@Import({ApplicationConfig.class})
@WebAppConfiguration
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